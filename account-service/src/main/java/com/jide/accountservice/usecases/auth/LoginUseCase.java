package com.jide.accountservice.usecases.auth;


import com.jide.accountservice.usecases.dto.request.LoginRequest;
import com.jide.accountservice.usecases.dto.response.LoginResponse;

public interface LoginUseCase {
    LoginResponse processLogin(LoginRequest loginRequest);
}
