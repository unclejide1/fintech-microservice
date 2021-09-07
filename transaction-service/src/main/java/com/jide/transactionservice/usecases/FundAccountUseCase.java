package com.jide.transactionservice.usecases;

import com.jide.transactionservice.usecases.dto.request.AccountFundingRequest;
import com.jide.transactionservice.usecases.dto.response.AccountFundingResponse;

public interface FundAccountUseCase {
    AccountFundingResponse fundAccount(AccountFundingRequest request, String userEmail);
}
