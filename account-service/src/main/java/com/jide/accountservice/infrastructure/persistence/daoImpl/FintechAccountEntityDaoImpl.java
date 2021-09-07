package com.jide.accountservice.infrastructure.persistence.daoImpl;


import com.jide.accountservice.domain.dao.FintechAccountEntityDao;
import com.jide.accountservice.domain.dao.RoleDao;
import com.jide.accountservice.domain.dao.SequenceEntityDao;
import com.jide.accountservice.domain.dao.UserDao;
import com.jide.accountservice.domain.entities.FintechAccountEntity;
import com.jide.accountservice.domain.entities.Role;
import com.jide.accountservice.domain.entities.User;
import com.jide.accountservice.domain.enums.RecordStatusConstant;
import com.jide.accountservice.domain.enums.SequenceType;
import com.jide.accountservice.infrastructure.persistence.repository.FintechAccountEntityRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.inject.Named;
import java.util.List;
import java.util.Optional;

@Named
public class FintechAccountEntityDaoImpl extends CrudDaoImpl<FintechAccountEntity, Long> implements FintechAccountEntityDao {

    private final SequenceEntityDao sequenceEntityDao;
    private final UserDao userDao;
    private final FintechAccountEntityRepository repository;

    public FintechAccountEntityDaoImpl(JpaRepository<FintechAccountEntity, Long> repository, SequenceEntityDao sequenceEntityDao, UserDao userDao, FintechAccountEntityRepository repository1) {
        super(repository);
        this.sequenceEntityDao = sequenceEntityDao;
        this.userDao = userDao;
        this.repository = repository1;
    }

    @Override
    public String generateAccountId() {
        return sequenceEntityDao.getNextAccountId();
    }

    @Override
    public Optional<FintechAccountEntity> findAccountByAccountId(String accountId) {
        return repository.findFirstByAccountId(accountId);
    }

    @Override
    public FintechAccountEntity getAccountByAccountId(String accountId) throws Exception {
        return findAccountByAccountId(accountId).orElseThrow(() -> new Exception("Not found. Account with No: "+accountId));
    }

    @Override
    public List<FintechAccountEntity> getAllAccounts() {
        return repository.getAllByRecordStatus(RecordStatusConstant.ACTIVE);
    }

    @Override
    public List<FintechAccountEntity> getAllAccountsByUser(User user) {
        return repository.getAllByCreatorAndRecordStatus(user, RecordStatusConstant.ACTIVE);
    }
}
