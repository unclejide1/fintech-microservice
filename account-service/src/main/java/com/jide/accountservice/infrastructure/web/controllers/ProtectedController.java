package com.jide.accountservice.infrastructure.web.controllers;



import com.jide.accountservice.infrastructure.apiresponse.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Validated
//@Api(tags = "Protected Endpoints")
@RestController
@RequestMapping(value = "api/v1/test")
//@Secured({"ROLE_MANAGER", "ROLE_ADMIN","ROLE_USER"})
@Secured({"ROLE_ADMIN"})
public class ProtectedController {
//    private final ManageUserUseCase manageUserUseCase;

//    @Autowired
//    public ProtectedController(ManageUserUseCase manageUserUseCase) {
//        this.manageUserUseCase = manageUserUseCase;
//    }


    @GetMapping("/user")
    public @ResponseBody
    ResponseEntity<ApiResponse<String>>  retrieveAllUsers(){
//       String trackingReference = sequenceEntityDao.getNextAccountId();
//       String trackingReference2 = sequenceEntityDao.getNextTransactionReference();
        String trackingReference = "tetrttrtrttr";
        String trackingReference2 = "yyytytyyt";
        ApiResponse<String> apiResponse = new ApiResponse<>(HttpStatus.OK);
        apiResponse.setData(trackingReference);
        apiResponse.setMessage(trackingReference2);
        return new ResponseEntity<>(apiResponse, apiResponse.getStatus());

    }
}
