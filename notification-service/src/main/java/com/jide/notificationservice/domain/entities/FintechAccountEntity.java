package com.jide.notificationservice.domain.entities;

import com.jide.notificationservice.domain.enums.AccountAccessStatusConstant;
import com.jide.notificationservice.domain.enums.AccountTypeConstant;
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
    private BigDecimal dailyTransactionLimit = BigDecimal.ZERO;


    
}
