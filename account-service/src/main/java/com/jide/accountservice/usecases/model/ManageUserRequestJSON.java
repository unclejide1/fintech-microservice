//package com.jide.accountservice.usecases.model;
//
//
//import com.example.betchess.usecases.dto.request.ManageUserRequest;
//import io.swagger.annotations.ApiModelProperty;
//import lombok.Data;
//
//import javax.validation.constraints.Email;
//import javax.validation.constraints.NotBlank;
//import java.util.Set;
//
//@Data
//public class ManageUserRequestJSON {
//    @Email(message = "Username must be an email")
//    @NotBlank(message = "Username is Required")
//    private String username;
//    @ApiModelProperty(notes = "Types: ADMIN,admin,USER,user,MANAGER,manager")
////    @Pattern(regexp = "(ADMIN|admin|USER|user|MANAGER|manager)")
//    private Set<String> roles;
//    @ApiModelProperty(notes = "Types: QA,qa,DEV,dev,HR,hr")
//    private String dept;
//
//    public ManageUserRequest toRequest(){
//        return ManageUserRequest.builder().username(username)
//                .roles(roles)
//                .dept(dept)
//                .build();
//    }
//}
