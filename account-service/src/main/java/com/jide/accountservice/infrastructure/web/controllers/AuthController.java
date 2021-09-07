package com.jide.accountservice.infrastructure.web.controllers;


import com.jide.accountservice.domain.dao.SequenceEntityDao;
import com.jide.accountservice.infrastructure.apiresponse.ApiResponse;
import com.jide.accountservice.usecases.auth.LoginUseCase;
import com.jide.accountservice.usecases.auth.SignUpUseCase;
import com.jide.accountservice.usecases.dto.request.LoginRequest;
import com.jide.accountservice.usecases.dto.response.LoginResponse;
import com.jide.accountservice.usecases.model.CreateUserJSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Validated
@Api(tags = "Authentication Endpoints")
@RestController
@RequestMapping(value = "api/v1/auth")
public class AuthController {

    @Value("${secured.app.jwtSecret}")
    private String jwtSecret;

    @Autowired
    private Environment env;

    private final SignUpUseCase signUpUseCase;
    private final LoginUseCase loginUseCase;

//    private final SequenceEntityDao sequenceEntityDao;
//
//    @Autowired
//    public AuthController(SequenceEntityDao sequenceEntityDao) {
//        this.sequenceEntityDao = sequenceEntityDao;
//    }

    @Autowired
    public AuthController(SignUpUseCase signUpUseCase, LoginUseCase loginUseCase) {
        this.signUpUseCase = signUpUseCase;
        this.loginUseCase = loginUseCase;
    }
//
    @ApiOperation(value = "register", notes = "This is used to create a user")
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<String>> signUp(@Valid @RequestBody CreateUserJSON requestJSON){
        String response = signUpUseCase.createUser(requestJSON.toRequest());
        ApiResponse<String> apiResponse = new ApiResponse<>(HttpStatus.CREATED);
        apiResponse.setData(response);
        apiResponse.setMessage("successfully created a user");
        return new ResponseEntity<>(apiResponse,apiResponse.getStatus());
    }




    @ApiOperation(value = "sign in", notes = "This is used to sign in into the platform")
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request){
        LoginResponse response = loginUseCase.processLogin(request);
        ApiResponse<LoginResponse> apiResponse = new ApiResponse<>(HttpStatus.OK);
        apiResponse.setData(response);
        apiResponse.setMessage("login successful");
        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }

//    @ApiOperation(value = "sign in", notes = "This is used to sign in into the platform")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<String>> verify(@PathVariable String id){
        String response = signUpUseCase.verifyUser(Long.valueOf(id));
        ApiResponse<String> apiResponse = new ApiResponse<>(HttpStatus.OK);
        apiResponse.setData(response);
        apiResponse.setMessage("login successful");
        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
    }
//
//    @GetMapping(value = "/testall")
//    public String general(){
//        return "all";
//    }
//
//
//    @GetMapping(value = "/admin")
//    @Secured("ROLE_ADMIN")
//    public String admin(){
//        return "admin";
//    }
//
   @GetMapping("/users")
    public @ResponseBody ResponseEntity<ApiResponse<String>>  retrieveAllUsers(){
//       String trackingReference = sequenceEntityDao.getNextAccountId();
//       String trackingReference2 = sequenceEntityDao.getNextTransactionReference();
       String trackingReference = "tetrttrtrttr";
       String trackingReference2 = env.getProperty("secured.app.jwtSecret");
       ApiResponse<String> apiResponse = new ApiResponse<>(HttpStatus.OK);
        apiResponse.setData(trackingReference);
        apiResponse.setMessage(trackingReference2);
        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());

    }

}
