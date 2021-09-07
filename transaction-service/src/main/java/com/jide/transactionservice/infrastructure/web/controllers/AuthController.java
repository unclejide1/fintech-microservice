package com.jide.transactionservice.infrastructure.web.controllers;


import com.jide.transactionservice.infrastructure.apiresponse.ApiResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping(value = "api/v1/auth")
public class AuthController {



//    @ApiOperation(value = "sign in", notes = "This is used to sign in into the platform")
    @GetMapping(value = "/check", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<String>> verify(Authentication authentication){
        String response = authentication.getName();
        ApiResponse<String> apiResponse = new ApiResponse<>(HttpStatus.OK);
        apiResponse.setData(response);
        apiResponse.setMessage("login successful");
        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }


}
