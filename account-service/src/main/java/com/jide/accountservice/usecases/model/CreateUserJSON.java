package com.jide.accountservice.usecases.model;


import com.jide.accountservice.usecases.dto.request.SignUpRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Data
public class CreateUserJSON {
    @ApiModelProperty(notes = "valid email", required = true)
    @Email(message = "Username must be an email")
    @NotBlank(message = "Username is Required")
    private String username;

    @ApiModelProperty(notes = "first name", required = true)
    @NotBlank(message = "Please Enter your first name")
    private String firstName;

    @ApiModelProperty(notes = "last name", required = true)
    @NotBlank(message = "Please Enter your last name")
    private String lastName;

    @ApiModelProperty(notes = "Format: dd/MM/yyyy", required = true)
    @NotBlank(message = "Please Enter your date of birth")
    private String dateOfBirth;

    @ApiModelProperty(notes = "phone number", required = true)
    @NotBlank(message = "Please provide a phone number")
    private String phoneNumber;

    @ApiModelProperty(notes = "Types: MALE,male,FEMALE,female", required = true)
    @Pattern(regexp = "(MALE|male|FEMALE|female)")
    private String gender;

    @ApiModelProperty(notes = "Types: ADMIN,admin,USER,user")
//    @Pattern(regexp = "(ADMIN|admin|USER|user)")
    private Set<String> roles;



    @ApiModelProperty(notes = "password", required = true)
    @NotBlank(message = "password is required")
    private String password;


    public SignUpRequest toRequest(){
        return SignUpRequest.builder().email(username)
                .firstName(firstName)
                .lastName(lastName)
                .dateOfBirth(LocalDate.parse(dateOfBirth, DateTimeFormatter.ofPattern("dd/MM/yyyy")))
                .phoneNumber(phoneNumber)
                .gender(gender)
                .roles(roles)
                .password(password)
                .build();
    }
}
