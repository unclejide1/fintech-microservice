package com.jide.notificationservice.domain.entities;

import com.jide.notificationservice.domain.enums.TransactionTypeConstant;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "account_transaction")
public class AccountTransactionEntity extends AbstractBaseEntity<Long> {

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private FintechAccountEntity bankAccount;

    @Column(nullable = false)
    private LocalDateTime transactionDate;


    @Column(nullable = false)
    private BigDecimal amount;

   // @Column(nullable = false)
    private BigDecimal balanceBeforeTransaction;

    @Column(nullable = false)
    private BigDecimal currentBalance;

    private String narration;


    @Column(nullable = false)
    private String transactionReference;

    @Enumerated(EnumType.STRING)
    private TransactionTypeConstant transactionType;



}
