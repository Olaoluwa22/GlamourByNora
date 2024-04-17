package com.GlamourByNora.api.service;

import com.GlamourByNora.api.dto.AuthenticationDto;
import com.GlamourByNora.api.dto.SignupRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    ResponseEntity<?> login(AuthenticationDto authenticationDto, HttpServletResponse response, HttpServletRequest request);
    ResponseEntity<?> logout(HttpServletRequest request);
    ResponseEntity<?> signup(SignupRequestDto signupDto);
}