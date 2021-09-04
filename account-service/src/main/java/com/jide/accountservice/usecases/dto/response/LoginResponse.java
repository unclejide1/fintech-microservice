package com.jide.accountservice.usecases.dto.response;


import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponse {
    private String username;
    private String token;
    private List<String> roles;
}
