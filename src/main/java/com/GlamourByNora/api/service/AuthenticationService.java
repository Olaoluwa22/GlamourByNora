package com.GlamourByNora.api.service;

import com.GlamourByNora.api.dto.AuthenticationDto;
import com.GlamourByNora.api.exception.exceptionHandler.UserNotLoggedInException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface AuthenticationService {
    public ResponseEntity<?> login(AuthenticationDto authenticationDto, HttpServletResponse response);
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response);
    public ResponseEntity<?> isUserLoggedIn(HttpServletRequest request) throws UserNotLoggedInException;

}
