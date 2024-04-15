package com.GlamourByNora.api.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.coyote.BadRequestException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private JwtService jwtService;
    public JwtAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            String token = jwtService.resolveToken(request);
            if(token != null && jwtService.isTokenExpired(token)){
                Authentication jwtServiceAuthentication = jwtService.getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(jwtServiceAuthentication);
            }
            filterChain.doFilter(request, response);
        }catch(Exception exception){
            throw new BadRequestException("Bad Request");
        }
    }
}
