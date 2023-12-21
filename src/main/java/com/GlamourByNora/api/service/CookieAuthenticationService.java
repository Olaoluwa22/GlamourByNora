package com.GlamourByNora.api.service;

import com.GlamourByNora.api.dto.AuthenticationDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface CookieAuthenticationService {
    public void login(AuthenticationDto authenticationDto, HttpServletResponse response);
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response);
    public ResponseEntity<?> getLoggedInUser(HttpServletRequest request);
}
