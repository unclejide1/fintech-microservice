package com.jide.accountservice.usecases.dto.request;


import lombok.*;

import java.math.BigDecimal;
import java.util.Set;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CreateAccountRequest {
    private String accountType;


    private BigDecimal limit;

}
