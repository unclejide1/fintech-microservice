package com.jide.transactionservice.infrastructure.web.controllers;


import com.jide.transactionservice.infrastructure.apiresponse.ApiResponse;
import com.jide.transactionservice.usecases.FundAccountUseCase;
import com.jide.transactionservice.usecases.dto.request.AccountFundingRequest;
import com.jide.transactionservice.usecases.dto.response.AccountFundingResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@Validated
@Api(tags = "Authentication Endpoints")
@RestController
@RequestMapping(value = "api/v1/account")
public class FundsAccountController {
    private FundAccountUseCase fundAccountUseCase;

    private static final Logger logger = LoggerFactory.getLogger(FundsAccountController.class);

    @Autowired
    public FundsAccountController(FundAccountUseCase fundAccountUseCase) {
        this.fundAccountUseCase = fundAccountUseCase;
    }

    //    @ApiOperation(value = "sign in", notes = "This is used to sign in into the platform")
    @PutMapping(value = "/fund", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<AccountFundingResponse>> verify(@Valid @RequestBody AccountFundingRequest request, Authentication authentication){
        AccountFundingResponse response = fundAccountUseCase.fundAccount(request, authentication.getName());
        ApiResponse<AccountFundingResponse> apiResponse = new ApiResponse<>(HttpStatus.OK);
        apiResponse.setData(response);
        apiResponse.setMessage("Account Funded Successfully");
        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }


}
