package com.jide.transactionservice.infrastructure.web.controllers;

import com.jide.transactionservice.infrastructure.apiresponse.ApiResponse;
import com.jide.transactionservice.usecases.AccountBalanceUseCase;
import com.jide.transactionservice.usecases.TransferFundUseCase;
import com.jide.transactionservice.usecases.dto.request.AccountFundingRequest;
import com.jide.transactionservice.usecases.dto.request.TransferFundRequest;
import com.jide.transactionservice.usecases.dto.response.AccountBalanceResponse;
import com.jide.transactionservice.usecases.dto.response.AccountFundingResponse;
import com.jide.transactionservice.usecases.dto.response.TransferFundResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@Api(tags = "Transaction Endpoints")
@RestController
@RequestMapping(value = "api/v1/transact")
public class TransactionController {

    private TransferFundUseCase transferFundUseCase;
    private AccountBalanceUseCase accountBalanceUseCase;

    public TransactionController(TransferFundUseCase transferFundUseCase, AccountBalanceUseCase accountBalanceUseCase) {
        this.transferFundUseCase = transferFundUseCase;
        this.accountBalanceUseCase = accountBalanceUseCase;
    }

    @ApiOperation(value = "send money", notes = "This is used to transfer funds", authorizations = { @Authorization(value="Authorization") })
    @PutMapping(value = "/send", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<TransferFundResponse>> transfer(@Valid @RequestBody TransferFundRequest request, Authentication authentication){
        TransferFundResponse response = transferFundUseCase.sendMoney(request, authentication.getName());
        ApiResponse<TransferFundResponse> apiResponse = new ApiResponse<>(HttpStatus.OK);
        apiResponse.setData(response);
        apiResponse.setMessage("Transfer Successful");
        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }

    @ApiOperation(value = "account balance", notes = "This is used to get account balance", authorizations = { @Authorization(value="Authorization") })
    @GetMapping(value = "/{accountNo}",  produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<AccountBalanceResponse>> getAccountBalance(@PathVariable String accountNo, Authentication authentication){
        AccountBalanceResponse response = accountBalanceUseCase.getAccountBalance(accountNo, authentication.getName());
        ApiResponse<AccountBalanceResponse> apiResponse = new ApiResponse<>(HttpStatus.OK);
        apiResponse.setData(response);
        apiResponse.setMessage("Retrieved AccountBalance successfully");
        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }
}
