package com.jide.accountservice.usecases;

import com.jide.accountservice.usecases.dto.request.CreateAccountRequest;
import com.jide.accountservice.usecases.dto.response.AccountCreationResponse;

public interface CreateAccountUseCase {

    AccountCreationResponse createAccount(CreateAccountRequest createAccountRequest, String userEmail);
}
