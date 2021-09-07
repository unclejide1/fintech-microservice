package com.jide.notificationservice.infrastructure.web.controllers;


import com.jide.notificationservice.infrastructure.apiresponse.ApiResponse;
import com.jide.notificationservice.usecases.NotificationUseCase;
import com.jide.notificationservice.usecases.model.MicroserviceRequest;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

//import org.springframework.security.access.annotation.Secured;

@Validated
@Api(tags = "Authentication Endpoints")
@RestController
@RequestMapping(value = "api/v1/notify")
public class NotificationController {



//    @Autowired
//    private Environment env;

    private final NotificationUseCase notificationUseCase;

    @Autowired
    public NotificationController(NotificationUseCase notificationUseCase) {
        this.notificationUseCase = notificationUseCase;
    }
//    private final LoginUseCase loginUseCase;

//    private final SequenceEntityDao sequenceEntityDao;
//
//    @Autowired
//    public AuthController(SequenceEntityDao sequenceEntityDao) {
//        this.sequenceEntityDao = sequenceEntityDao;
//    }

//    @Autowired
//    public AuthController(SignUpUseCase signUpUseCase, LoginUseCase loginUseCase) {
//        this.signUpUseCase = signUpUseCase;
//        this.loginUseCase = loginUseCase;
//    }
//
//    @ApiOperation(value = "register", notes = "This is used to create a user")
    @PostMapping(value = "/send", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<String>> signUp(@Valid @RequestBody MicroserviceRequest requestJSON){
        String response = notificationUseCase.sendMessage(requestJSON);
        ApiResponse<String> apiResponse = new ApiResponse<>(HttpStatus.CREATED);
        apiResponse.setData(response);
        apiResponse.setMessage("successfully sent notification");
        return new ResponseEntity<>(apiResponse,apiResponse.getStatus());
    }




//    @ApiOperation(value = "sign in", notes = "This is used to sign in into the platform")
//    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request){
//        LoginResponse response = loginUseCase.processLogin(request);
//        ApiResponse<LoginResponse> apiResponse = new ApiResponse<>(HttpStatus.OK);
//        apiResponse.setData(response);
//        apiResponse.setMessage("login successful");
//        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
//    }
////
////    @GetMapping(value = "/testall")
////    public String general(){
////        return "all";
////    }
////
////
////    @GetMapping(value = "/admin")
////    @Secured("ROLE_ADMIN")
////    public String admin(){
////        return "admin";
////    }
////
//   @GetMapping("/users")
//    public @ResponseBody ResponseEntity<ApiResponse<String>>  retrieveAllUsers(){
////       String trackingReference = sequenceEntityDao.getNextAccountId();
////       String trackingReference2 = sequenceEntityDao.getNextTransactionReference();
//       String trackingReference = "tetrttrtrttr";
//       String trackingReference2 = env.getProperty("secured.app.jwtSecret");
//       ApiResponse<String> apiResponse = new ApiResponse<>(HttpStatus.OK);
//        apiResponse.setData(trackingReference);
//        apiResponse.setMessage(trackingReference2);
//        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());
//
//    }

}
