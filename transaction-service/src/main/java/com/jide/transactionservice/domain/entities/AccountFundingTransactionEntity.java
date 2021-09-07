package com.jide.transactionservice.domain.entities;


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
@Table(name = "account_funding_transaction")
public class AccountFundingTransactionEntity extends AbstractBaseEntity<Long> {

    @Column(nullable = false, unique = true)
    private String transactionReference;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private FintechAccountEntity bankAccount;


    @Column(nullable = false)
    private BigDecimal amount;

    private LocalDateTime paymentDate;




}
