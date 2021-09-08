package com.jide.notificationservice.infrastructure.persistence.repository;



import com.jide.notificationservice.domain.entities.AccountTransactionEntity;
import com.jide.notificationservice.domain.entities.FintechAccountEntity;
import com.jide.notificationservice.domain.enums.TransactionTypeConstant;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface AccountTransactionRepository extends JpaRepository<AccountTransactionEntity, Long> {
    Optional<AccountTransactionEntity> findFirstByBankAccountAndTransactionReference(FintechAccountEntity bankAccount, String reference);
    List<AccountTransactionEntity> getAllByBankAccountAndTransactionDateBetweenOrderByTransactionDateDesc(FintechAccountEntity bankAccount,
                                                                                                          LocalDateTime startDate, LocalDateTime endDate);
    List<AccountTransactionEntity> getAllByBankAccountOrderByTransactionDateDesc(FintechAccountEntity account, Pageable pageable);
    List<AccountTransactionEntity> getAllByBankAccountAndTransactionTypeAndTransactionDateBetweenOrderByTransactionDateDesc(FintechAccountEntity account, TransactionTypeConstant transactionTypeConstant, LocalDateTime startDate, LocalDateTime endDate);
}
