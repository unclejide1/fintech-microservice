package com.jide.transactionservice.infrastructure.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

    @Autowired
    private Environment env;



    @Autowired
    private JwtConfig jwtConfig;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable();
        http.headers().frameOptions().disable();
        http.authorizeRequests()
                .antMatchers(jwtConfig.getUri()).permitAll()
                .antMatchers(env.getProperty("api.transaction.actuator.url.path")).permitAll()
                .antMatchers(env.getProperty("api.zuul.actuator.url.path")).permitAll()
//                .antMatchers(HttpMethod.POST, environment.getProperty("api.registration.url.path")).permitAll()
//                .antMatchers(HttpMethod.POST, environment.getProperty("api.login.url.path")).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterAfter(new JwtTokenAuthenticationFilter(authenticationManager(),jwtConfig), UsernamePasswordAuthenticationFilter.class);
//                .addFilter(new AuthorizationFilter(authenticationManager(), environment));

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//        http
//                .csrf().disable()
//                // make sure we use stateless session; session won't be used to store user's state.
//                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
//                .and()
//                // handle an authorized attempts
//                .exceptionHandling().authenticationEntryPoint((req, rsp, e) -> rsp.sendError(HttpServletResponse.SC_BAD_GATEWAY))
//                .and()
//                // Add a filter to validate the tokens with every request
//                .addFilterAfter(new JwtTokenAuthenticationFilter(jwtConfig), UsernamePasswordAuthenticationFilter.class)
//                // authorization requests config
//                .authorizeRequests()
//                // allow all who are accessing "auth" service
//                .antMatchers(HttpMethod.POST, jwtConfig.getUri()).permitAll()
//                // must be an admin if trying to access admin area (authentication is also required here)
////                .antMatchers("/gallery" + "/admin/**").hasRole("ADMIN")
//                // Any ot   her request must be authenticated
//                .anyRequest().authenticated();
    }


    @Bean
    public JwtConfig jwtConfig() {
        return new JwtConfig();
    }
}
