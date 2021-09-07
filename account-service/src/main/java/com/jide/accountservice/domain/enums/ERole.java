package com.jide.accountservice.domain.enums;

import org.springframework.security.core.GrantedAuthority;

public enum ERole implements GrantedAuthority {
    ROLE_ADMIN, ROLE_USER, ROLE_MANAGER;

    @Override
    public String getAuthority() {
        return name();
    }
}
