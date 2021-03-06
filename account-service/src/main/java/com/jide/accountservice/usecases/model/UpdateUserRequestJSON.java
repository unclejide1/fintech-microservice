package com.jide.accountservice.usecases.model;

import com.jide.accountservice.usecases.dto.request.UpdateUserDetailsRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
public class UpdateUserRequestJSON {


    @ApiModelProperty(notes = "first name")
    private String firstName;

    @ApiModelProperty(notes = "last name")
    private String lastName;



    @ApiModelProperty(notes = "phone number")
    private String phoneNumber;

    @ApiModelProperty(notes = "Types: MALE,male,FEMALE,female")
    @Pattern(regexp = "(MALE|male|FEMALE|female)")
    private String gender;



    public UpdateUserDetailsRequest toRequest(){
        return UpdateUserDetailsRequest.builder()
                .firstName(firstName)
                .lastName(lastName)
                .phoneNumber(phoneNumber)
                .gender(gender)
                .build();
    }
}
