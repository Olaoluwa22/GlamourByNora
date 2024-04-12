package com.GlamourByNora.api.jwt;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
public class JwtConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    private JwtService jwtService;

    public void configure(HttpSecurity httpSecurity){
        JwtTokenFilter customJwtTokenFilter = new JwtTokenFilter(jwtService);
        httpSecurity.addFilterBefore(customJwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }
}