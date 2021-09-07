package com.jide.accountservice.usecases.auth;


import com.jide.accountservice.usecases.dto.request.SignUpRequest;

public interface SignUpUseCase {
    String createUser(SignUpRequest signUpRequest);
    String verifyUser(Long id);
}
