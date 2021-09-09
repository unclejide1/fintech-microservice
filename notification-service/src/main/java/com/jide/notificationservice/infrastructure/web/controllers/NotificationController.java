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


@Validated
@Api(tags = "Notification Endpoints")
@RestController
@RequestMapping(value = "api/v1/notify")
public class NotificationController {





    private final NotificationUseCase notificationUseCase;

    @Autowired
    public NotificationController(NotificationUseCase notificationUseCase) {
        this.notificationUseCase = notificationUseCase;
    }
//    @ApiOperation(value = "register", notes = "This is used to create a user")
    @PostMapping(value = "/send", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ApiResponse<String>> signUp(@Valid @RequestBody MicroserviceRequest requestJSON){
        String response = notificationUseCase.sendMessage(requestJSON);
        ApiResponse<String> apiResponse = new ApiResponse<>(HttpStatus.CREATED);
        apiResponse.setData(response);
        apiResponse.setMessage("successfully sent notification");
        return new ResponseEntity<>(apiResponse,apiResponse.getStatus());
    }




}
