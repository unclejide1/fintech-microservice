package com.jide.accountservice.domain.dao;

import com.jide.accountservice.domain.entities.FintechAccountEntity;

import java.util.List;
import java.util.Optional;

public interface FintechAccountEntityDao extends CrudDao<FintechAccountEntity, Long> {

    String generateAccountId();
    Optional<FintechAccountEntity> findAccountByAccountId(String accountId);
    FintechAccountEntity getAccountByAccountId(String accountId);
    List<FintechAccountEntity> getAllAccounts();
    List<FintechAccountEntity> getAllAccountsByUser();
}
