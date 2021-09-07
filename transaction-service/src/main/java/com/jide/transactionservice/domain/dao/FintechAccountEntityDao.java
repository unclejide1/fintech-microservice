package com.jide.transactionservice.domain.dao;



import com.jide.transactionservice.domain.entities.FintechAccountEntity;
import com.jide.transactionservice.domain.entities.User;

import java.util.List;
import java.util.Optional;

public interface FintechAccountEntityDao extends CrudDao<FintechAccountEntity, Long> {

    String generateAccountId();
    Optional<FintechAccountEntity> findAccountByAccountId(String accountId);
    FintechAccountEntity getAccountByAccountId(String accountId) throws Exception;
    List<FintechAccountEntity> getAllAccounts();
    List<FintechAccountEntity> getAllAccountsByUser(User user);
}
