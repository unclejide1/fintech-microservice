package com.jide.accountservice.infrastructure.web.controllers;



import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@Validated
//@Api(tags = "Protected Endpoints")
@RestController
@RequestMapping(value = "api/v1/test")
//@Secured({"ROLE_MANAGER", "ROLE_ADMIN","ROLE_USER"})
public class ProtectedController {
//    private final ManageUserUseCase manageUserUseCase;

//    @Autowired
//    public ProtectedController(ManageUserUseCase manageUserUseCase) {
//        this.manageUserUseCase = manageUserUseCase;
//    }



}
