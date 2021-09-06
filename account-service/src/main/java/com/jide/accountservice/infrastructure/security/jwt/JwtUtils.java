package com.jide.accountservice.infrastructure.security.jwt;



import com.jide.accountservice.infrastructure.exceptions.CustomException;
import com.jide.accountservice.infrastructure.security.AuthenticatedUser;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);


    @Value("${secured.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    @Autowired
    private Environment env;

    public String generateJwtToken(Authentication authentication) {

        AuthenticatedUser userPrincipal = (AuthenticatedUser) authentication.getPrincipal();

        return Jwts.builder()
                .setSubject((userPrincipal.getUsername()))
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(SignatureAlgorithm.HS512, env.getProperty("secured.app.jwtSecret"))
                .compact();
    }

    public String getUserNameFromJwtToken(String token) {
        return Jwts.parser().setSigningKey(env.getProperty("secured.app.jwtSecret")).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(env.getProperty("secured.app.jwtSecret")).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
            throw new CustomException("Sorry your token has an invalid jwt signature", HttpStatus.BAD_REQUEST);
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
            throw new CustomException("Sorry your token is malformed", HttpStatus.BAD_REQUEST);
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
            throw new CustomException("Sorry your token has expired", HttpStatus.BAD_REQUEST);
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }


}
