package com.jide.accountservice.usecases.dto.response;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountCreationResponse {
    private String accountNo;
    private String accountType;
    private String limit;
    private String availableBalance;

}
