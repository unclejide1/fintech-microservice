//package com.jide.accountservice.usecases.model;
//
//import com.example.betchess.usecases.dto.request.UpdateUserDetailsRequest;
//import io.swagger.annotations.ApiModelProperty;
//import lombok.Data;
//
//import javax.validation.constraints.Email;
//import javax.validation.constraints.NotNull;
//import javax.validation.constraints.Pattern;
//import java.time.LocalDate;
//import java.time.format.DateTimeFormatter;
//
//@Data
//public class UpdateUserRequestJSON {
//    @ApiModelProperty(notes = "valid user ID", required = true)
//    @NotNull(message = "must pass an id")
//    private Long id;
//
//    @ApiModelProperty(notes = "valid email")
//    @Email(message = "Username must be an email")
//    private String username;
//
//    @ApiModelProperty(notes = "first name")
//    private String firstName;
//
//    @ApiModelProperty(notes = "last name")
//    private String lastName;
//
//    @ApiModelProperty(notes = "middle name")
//    private String middleName;
//
//    @ApiModelProperty(notes = "Format: dd/MM/yyyy")
//    private String dateOfBirth;
//
//    @ApiModelProperty(notes = "phone number")
//    private String phoneNumber;
//
//    @ApiModelProperty(notes = "Types: MALE,male,FEMALE,female")
//    @Pattern(regexp = "(MALE|male|FEMALE|female)")
//    private String gender;
//
//
//
//    public UpdateUserDetailsRequest toRequest(){
//        return UpdateUserDetailsRequest.builder().username(username)
//                .id(id)
//                .firstName(firstName)
//                .lastName(lastName)
//                .middleName(middleName)
//                .dateOfBirth(LocalDate.parse(dateOfBirth, DateTimeFormatter.ofPattern("dd/MM/yyyy")))
//                .phoneNumber(phoneNumber)
//                .gender(gender)
//                .build();
//    }
//}
