package com.jide.transactionservice.usecases.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransferFundResponse{
    private String availableBalance;
    private String accountNumber;
    private String amountSent;

}
