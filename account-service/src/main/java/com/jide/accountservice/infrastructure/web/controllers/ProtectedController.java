package com.jide.accountservice.infrastructure.web.controllers;



import com.jide.accountservice.infrastructure.apiresponse.ApiResponse;
//import com.jide.accountservice.infrastructure.security.AuthenticatedUser;
import com.jide.accountservice.infrastructure.security.AuthenticatedUser;
import com.jide.accountservice.infrastructure.security.jwt.JwtUtils;
import com.jide.accountservice.usecases.CreateAccountUseCase;
import com.jide.accountservice.usecases.UpdateUserDetailsUseCase;
import com.jide.accountservice.usecases.dto.request.LoginRequest;
import com.jide.accountservice.usecases.dto.response.AccountCreationResponse;
import com.jide.accountservice.usecases.dto.response.UserDetailsResponse;
import com.jide.accountservice.usecases.model.CreateAccountRequestJSON;
import com.jide.accountservice.usecases.model.UpdateUserRequestJSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;

@Validated
@Api(tags = "Fintech account creation Endpoints")
@RestController
@RequestMapping(value = "api/v1/profile")
@Secured("ROLE_USER")
public class ProtectedController {
    private final CreateAccountUseCase createAccountUseCase;
    private final UpdateUserDetailsUseCase updateUserDetailsUseCase;

    private static final Logger logger = LoggerFactory.getLogger(ProtectedController.class);


    @Autowired
    public ProtectedController(CreateAccountUseCase createAccountUseCase, UpdateUserDetailsUseCase updateUserDetailsUseCase) {
        this.createAccountUseCase = createAccountUseCase;
        this.updateUserDetailsUseCase = updateUserDetailsUseCase;
    }

    @ApiOperation(value = "create account", notes = "This is used to create account", authorizations = { @Authorization(value="Authorization") })
    @PostMapping(value = "/create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<ApiResponse<AccountCreationResponse>>  createAccount(@Valid @RequestBody CreateAccountRequestJSON createAccountRequestJSON, Authentication authentication){
        String email = StringUtils.EMPTY;
        if(authentication !=null){
            email = authentication.getName();
        }
        AccountCreationResponse accountCreationResponse = createAccountUseCase.createAccount(createAccountRequestJSON.toRequest(), email);
        ApiResponse<AccountCreationResponse> apiResponse = new ApiResponse<>(HttpStatus.CREATED);
        apiResponse.setData(accountCreationResponse);
        apiResponse.setMessage("Account created successfully");
        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());

    }

    @ApiOperation(value = "update profile", notes = "This is used to update profile", authorizations = { @Authorization(value="Authorization") })
    @PutMapping(value = "/update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody ResponseEntity<ApiResponse<UserDetailsResponse>>  updateProfile(@Valid @RequestBody UpdateUserRequestJSON updateUserRequestJSON, Authentication authentication){
        String email = StringUtils.EMPTY;
        if(authentication !=null){
            email = authentication.getName();
        }        UserDetailsResponse userDetailsResponse = updateUserDetailsUseCase.updateUserDetails(updateUserRequestJSON.toRequest(), email);
        ApiResponse<UserDetailsResponse> apiResponse = new ApiResponse<>(HttpStatus.CREATED);
        apiResponse.setData(userDetailsResponse);
        apiResponse.setMessage("Details have been updated successfully");
        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());

    }
}
