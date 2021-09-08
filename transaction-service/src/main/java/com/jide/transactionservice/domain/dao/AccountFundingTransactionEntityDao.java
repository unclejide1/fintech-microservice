package com.jide.transactionservice.domain.dao;



import com.jide.transactionservice.domain.entities.AccountFundingTransactionEntity;
import com.jide.transactionservice.domain.entities.FintechAccountEntity;

import java.util.List;
import java.util.Optional;

public interface AccountFundingTransactionEntityDao extends CrudDao<AccountFundingTransactionEntity, Long> {
    Optional<AccountFundingTransactionEntity> findAccountFundingTransactionByReference(String transactionReference);
    List<AccountFundingTransactionEntity> getCustomerAccountFundingTransactions(FintechAccountEntity mintAccountEntity);
}
