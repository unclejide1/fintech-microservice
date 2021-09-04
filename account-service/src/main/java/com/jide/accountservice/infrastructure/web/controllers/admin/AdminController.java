package com.jide.accountservice.infrastructure.web.controllers.admin;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@Validated
@Api(tags = "Admin Endpoints", description = "Endpoints for admin operations")
@RestController
//@Secured("ROLE_ADMIN")
@RequestMapping(value = "api/v1/admin")
public class AdminController {

//    private final SignUpUseCase signUpUseCase;
//    private final ManageUserUseCase manageUserUseCase;

//    @Autowired
//    public AdminController(SignUpUseCase signUpUseCase, ManageUserUseCase manageUserUseCase) {
//        this.signUpUseCase = signUpUseCase;
//        this.manageUserUseCase = manageUserUseCase;
//    }
//
//
//
//
//    @ApiOperation(value = "create admin user", notes = "This endpoint is used to create an admin user", authorizations = { @Authorization(value="Authorization") })
//    @PostMapping(value = "/createAdmin", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<ApiResponse<String>> create(@Valid @RequestBody CreateUserJSON requestJSON){
//        String response = signUpUseCase.createUser(requestJSON.toRequest());
//        ApiResponse<String> apiResponse = new ApiResponse<>(HttpStatus.CREATED);
//        apiResponse.setData(response);
//        apiResponse.setMessage("successfully created a user");
//        return new ResponseEntity<>(apiResponse,apiResponse.getStatus());
//
//    }





}
