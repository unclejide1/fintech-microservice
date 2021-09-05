package com.jide.gatewayservice.security;

import org.springframework.beans.factory.annotation.Value;

public class JwtConfig {
    @Value("${secured.app.uri:accounts/api/v1/auth/**}")
    private String Uri;

    @Value("${secured.app.header:Authorization}")
    private String header;

    @Value("${secured.app.prefix:Bearer }")
    private String prefix;

    @Value("${secured.app.jwtExpirationMs}")
    private int expiration;

    @Value("${secured.app.jwtSecret}")
    private String secret;

    public String getUri() {
        return Uri;
    }

    public String getHeader() {
        return header;
    }

    public String getPrefix() {
        return prefix;
    }

    public int getExpiration() {
        return expiration;
    }

    public String getSecret() {
        return secret;
    }
}
