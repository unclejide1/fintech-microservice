package com.jide.transactionservice.domain.dao;



import com.jide.transactionservice.domain.entities.AccountTransactionEntity;
import com.jide.transactionservice.domain.entities.FintechAccountEntity;
import com.jide.transactionservice.domain.enums.TransactionTypeConstant;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface AccountTransactionEntityDao extends CrudDao<AccountTransactionEntity, Long> {
    Optional<AccountTransactionEntity> findByAccountAndReference(FintechAccountEntity bankAccountEntity, String reference);
    List<AccountTransactionEntity> getTransactionsOnAccountWithinPeriod(FintechAccountEntity bankAccountEntity, LocalDateTime startDate, LocalDateTime endDate);
    List<AccountTransactionEntity> getTransactionsOnAccountWithinPeriodByTransactionType(FintechAccountEntity bankAccountEntity, TransactionTypeConstant transactionTypeConstant, LocalDateTime startDate, LocalDateTime endDate);
    List<AccountTransactionEntity> getTransactionsOnAccount(FintechAccountEntity bankAccountEntity, int size);
}
