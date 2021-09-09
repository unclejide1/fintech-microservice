package com.jide.gatewayservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletResponse;
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
                .antMatchers(env.getProperty("api.account.actuator.url.path")).permitAll()
                .antMatchers(env.getProperty("api.transaction.actuator.url.path")).permitAll()
                .antMatchers(env.getProperty("api.notification.actuator.url.path")).permitAll()
                .antMatchers(env.getProperty("api.zuul.actuator.url.path")).permitAll()
                .antMatchers(env.getProperty("api.account.swagger.url.patha")).permitAll()
                .antMatchers(env.getProperty("api.account.swagger.url.pathb")).permitAll()
                .antMatchers(env.getProperty("api.account.swagger.url.pathc")).permitAll()
                .antMatchers(env.getProperty("api.account.swagger.url.pathd")).permitAll()
                .antMatchers(env.getProperty("api.transaction.swagger.url.patha")).permitAll()
                .antMatchers(env.getProperty("api.transaction.swagger.url.pathb")).permitAll()
                .antMatchers(env.getProperty("api.transaction.swagger.url.pathc")).permitAll()
                .antMatchers(env.getProperty("api.transaction.swagger.url.pathd")).permitAll()
                .antMatchers(env.getProperty("api.notification.swagger.url.patha")).permitAll()
                .antMatchers(env.getProperty("api.notification.swagger.url.pathb")).permitAll()
                .antMatchers(env.getProperty("api.notification.swagger.url.pathc")).permitAll()
                .antMatchers(env.getProperty("api.notification.swagger.url.pathd")).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterAfter(new JwtTokenAuthenticationFilter(authenticationManager(),jwtConfig), UsernamePasswordAuthenticationFilter.class);

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
    }


    @Bean
    public JwtConfig jwtConfig() {
        return new JwtConfig();
    }
}
