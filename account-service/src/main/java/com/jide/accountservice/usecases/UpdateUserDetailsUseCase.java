package com.jide.accountservice.usecases;

import com.jide.accountservice.usecases.dto.request.UpdateUserDetailsRequest;
import com.jide.accountservice.usecases.dto.response.UserDetailsResponse;

public interface UpdateUserDetailsUseCase {
    UserDetailsResponse updateUserDetails(UpdateUserDetailsRequest updateUserDetailsRequest, String userEmail);
}
