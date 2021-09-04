package com.jide.accountservice.usecases.dto.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ManageUserRequest {
    private String username;

    private Set<String> roles;
    private String dept;

}
