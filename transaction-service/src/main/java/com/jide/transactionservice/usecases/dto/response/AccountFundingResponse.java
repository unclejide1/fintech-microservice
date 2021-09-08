package com.jide.transactionservice.usecases.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountFundingResponse {
    private String accountNo;
    private String accountType;
    private String amountFunded;
    private String availableBalance;
    private String transactionReference;

}
