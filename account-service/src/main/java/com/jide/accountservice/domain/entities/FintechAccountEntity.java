package com.jide.accountservice.domain.entities;

import com.jide.accountservice.domain.enums.AccountAccessStatusConstant;
import com.jide.accountservice.domain.enums.AccountTypeConstant;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;


@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "fin_account")
public class FintechAccountEntity extends AbstractBaseEntity<Long> {


    @Column(nullable = false, unique = true)
    private String accountId;


    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountTypeConstant accountType;


    @Builder.Default
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private AccountAccessStatusConstant accessStatus = AccountAccessStatusConstant.ACTIVE;

    @ManyToOne(optional = true, fetch = FetchType.LAZY)
    private User creator;

    @Builder.Default
    private BigDecimal availableBalance = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal ledgerBalance = BigDecimal.ZERO;

    @Builder.Default
    private BigDecimal dailyTransactionLimit = BigDecimal.ZERO;


    
}
