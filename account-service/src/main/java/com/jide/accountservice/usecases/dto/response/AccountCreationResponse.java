package com.jide.accountservice.usecases.dto.response;

import lombok.*;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class AccountCreationResponse {
    private String accountNo;
    private String accountType;
    private String limit;
    private String availableBalance;


}
