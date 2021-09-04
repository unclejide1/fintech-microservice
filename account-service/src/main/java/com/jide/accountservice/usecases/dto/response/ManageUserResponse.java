package com.jide.accountservice.usecases.dto.response;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ManageUserResponse {

    private String username;

    private Set<String> roles;

    private String dept;

}
