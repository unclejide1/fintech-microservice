package com.jide.transactionservice.usecases.dto.response;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccountBalanceResponse {
    private String accountNo;
    private String accountBalance;
    private String accountType;
}
