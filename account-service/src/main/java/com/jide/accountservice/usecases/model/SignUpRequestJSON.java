//package com.jide.accountservice.usecases.model;
//
//
////import io.swagger.annotations.ApiModelProperty;
//import com.example.betchess.usecases.dto.request.SignUpRequest;
//import io.swagger.annotations.ApiModelProperty;
//import lombok.Data;
//
//import javax.persistence.Transient;
//import javax.validation.constraints.Email;
//import javax.validation.constraints.NotBlank;
//import javax.validation.constraints.Pattern;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//import java.util.Set;
//
//@Data
//public class SignUpRequestJSON {
//
//    @ApiModelProperty(notes = "valid email", required = true)
//    @Email(message = "Username must be an email")
//    @NotBlank(message = "Username is Required")
//    private String username;
//
//    @ApiModelProperty(notes = "first name", required = true)
//    @NotBlank(message = "Please Enter your first name")
//    private String firstName;
//
//    @ApiModelProperty(notes = "last name", required = true)
//    @NotBlank(message = "Please Enter your last name")
//    private String lastName;
//
//    @ApiModelProperty(notes = "middle name", required = true)
//    @NotBlank(message = "Please Enter your middle name")
//    private String middleName;
//
//    @ApiModelProperty(notes = "Format: dd/MM/yyyy", required = true)
//    @NotBlank(message = "Please Enter your date of birth")
//    private String dateOfBirth;
//
//    @ApiModelProperty(notes = "phone number", required = true)
//    @NotBlank(message = "Please provide a phone number")
//    private String phoneNumber;
//
//    @ApiModelProperty(notes = "Types: MALE,male,FEMALE,female", required = true)
//    @Pattern(regexp = "(MALE|male|FEMALE|female)")
//    private String gender;
//
//    @ApiModelProperty(notes = "Types: ADMIN,admin,USER,user,MANAGER,manager")
////    @Patternzern(regexp = "(ADMIN|admin|USER|user)")
//    private Set<String> roles;
//
//
//    @ApiModelProperty(notes = "password", required = true)
//    @NotBlank(message = "password is required")
//    private String password;
//
//    @Transient
//    private String confirmPassword;
//
//    public SignUpRequest toRequest(){
//        return SignUpRequest.builder().username(username)
//                .firstName(firstName)
//                .lastName(lastName)
//                .middleName(middleName)
//                .dateOfBirth(LocalDate.parse(dateOfBirth, DateTimeFormatter.ofPattern("dd/MM/yyyy")))
//                .phoneNumber(phoneNumber)
//                .gender(gender)
//                .roles(roles)
//                .password(password)
//                .confirmPassword(confirmPassword)
//                .build();
//    }
//}
