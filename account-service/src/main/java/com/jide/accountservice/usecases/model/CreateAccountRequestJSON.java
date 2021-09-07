package com.jide.accountservice.usecases.model;



import com.jide.accountservice.usecases.dto.request.CreateAccountRequest;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


@Data
public class CreateAccountRequestJSON {

    @NotBlank(message = "Account Type is Required")
    @ApiModelProperty(notes = "Types: current,CURRENT,savings,SAVINGS")
    private String accountType;


    @ApiModelProperty(notes = "Daily Limit Amount", required = true)
    @NotNull(message = "must pass Limit Amount")
    private BigDecimal limit;



    public CreateAccountRequest toRequest(){
        return CreateAccountRequest.builder()
                .accountType(accountType)
                .limit(limit)
                .build();
    }
}
