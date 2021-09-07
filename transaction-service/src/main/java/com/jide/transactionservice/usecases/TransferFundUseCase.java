package com.jide.transactionservice.usecases;

import com.jide.transactionservice.usecases.dto.request.TransferFundRequest;
import com.jide.transactionservice.usecases.dto.response.TransferFundResponse;

public interface TransferFundUseCase {
    TransferFundResponse sendMoney(TransferFundRequest transferFundRequest, String userEmail);
}
