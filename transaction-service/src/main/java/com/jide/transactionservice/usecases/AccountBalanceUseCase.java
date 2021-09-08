package com.jide.transactionservice.usecases;

import com.jide.transactionservice.usecases.dto.response.AccountBalanceResponse;

public interface AccountBalanceUseCase {

    AccountBalanceResponse getAccountBalance(String accountNo, String userEmail);
}
