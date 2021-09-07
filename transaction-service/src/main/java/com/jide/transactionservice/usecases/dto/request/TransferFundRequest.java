package com.jide.transactionservice.usecases.dto.request;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Builder
public class TransferFundRequest {
    private String fundingAccount;
    private String receivingAccount;
    private BigDecimal amount;
    private String narration;
}
