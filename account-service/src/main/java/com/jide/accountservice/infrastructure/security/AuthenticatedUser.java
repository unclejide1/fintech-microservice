package com.jide.accountservice.infrastructure.security;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jide.accountservice.domain.entities.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
public class AuthenticatedUser implements UserDetails {
    private Long id;
    private String username;

    @JsonIgnore
    private String password;
    private Collection<? extends GrantedAuthority> grantedAuthorities;
    private boolean active = true;

    public AuthenticatedUser(Long id, String username, String password, List<? extends GrantedAuthority> grantedAuthorities) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.grantedAuthorities = grantedAuthorities;

    }

    public static AuthenticatedUser build(User user) {
        List<GrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());

        return new AuthenticatedUser(
                user.getId(),
                user.getEmail(),
                user.getPassword(),
                authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return active;
    }

    @Override
    public boolean isAccountNonLocked() {
        return active;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return active;
    }

    @Override
    public boolean isEnabled() {
        return active;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        AuthenticatedUser user = (AuthenticatedUser) o;
        return Objects.equals(id, user.id);
    }
}
