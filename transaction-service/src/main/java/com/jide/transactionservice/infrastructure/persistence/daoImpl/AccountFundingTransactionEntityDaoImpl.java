package com.jide.transactionservice.infrastructure.persistence.daoImpl;


import com.jide.transactionservice.domain.dao.AccountFundingTransactionEntityDao;
import com.jide.transactionservice.domain.entities.AccountFundingTransactionEntity;
import com.jide.transactionservice.domain.entities.FintechAccountEntity;
import com.jide.transactionservice.domain.enums.RecordStatusConstant;
import com.jide.transactionservice.infrastructure.persistence.repository.AccountFundingTransactionRepository;
import lombok.extern.slf4j.Slf4j;

import javax.inject.Named;
import java.util.List;
import java.util.Optional;


@Slf4j
@Named
public class AccountFundingTransactionEntityDaoImpl implements AccountFundingTransactionEntityDao {

    private AccountFundingTransactionRepository repository;
    public AccountFundingTransactionEntityDaoImpl(AccountFundingTransactionRepository repository) {
        this.repository = repository;
    }

    @Override
    public Optional<AccountFundingTransactionEntity> findAccountFundingTransactionByReference(String transactionReference) {
        return repository.findFirstByTransactionReference(transactionReference);
    }

    @Override
    public List<AccountFundingTransactionEntity> getCustomerAccountFundingTransactions(FintechAccountEntity accountEntity) {
        return repository.getAllByRecordStatusAndBankAccount(RecordStatusConstant.ACTIVE, accountEntity);
    }

    @Override
    public Optional<AccountFundingTransactionEntity> findById(Long aLong) {
        return repository.findById(aLong);
    }

    @Override
    public AccountFundingTransactionEntity getRecordById(Long aLong) throws RuntimeException {
        return findById(aLong).orElseThrow(() -> new RuntimeException("Not found: AccountFundingTransactionEntity with Id: "+aLong));
    }

    @Override
    public AccountFundingTransactionEntity saveRecord(AccountFundingTransactionEntity record) {
        return repository.save(record);
    }
}
