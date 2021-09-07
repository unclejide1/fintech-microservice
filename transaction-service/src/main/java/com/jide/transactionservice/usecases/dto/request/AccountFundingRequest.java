package com.jide.transactionservice.usecases.dto.request;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class AccountFundingRequest {
    private String accountNo;
    private BigDecimal amount;

}
