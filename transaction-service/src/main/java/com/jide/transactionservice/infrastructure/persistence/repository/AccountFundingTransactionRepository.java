package com.jide.transactionservice.infrastructure.persistence.repository;


import com.jide.transactionservice.domain.entities.AccountFundingTransactionEntity;
import com.jide.transactionservice.domain.entities.FintechAccountEntity;
import com.jide.transactionservice.domain.enums.RecordStatusConstant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface AccountFundingTransactionRepository extends JpaRepository<AccountFundingTransactionEntity, Long> {
    Optional<AccountFundingTransactionEntity> findFirstByTransactionReference(String reference);
    List<AccountFundingTransactionEntity> getAllByRecordStatusAndBankAccount(RecordStatusConstant statusConstant, FintechAccountEntity accountEntity);
//    List<AccountFundingTransactionEntity> getAllByRecordStatusAndBankAccount(RecordStatusConstant statusConstant, FintechAccountEntity bankAccountEntity);
}
