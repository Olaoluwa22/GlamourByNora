package com.GlamourByNora.api.service;

import com.GlamourByNora.api.dto.AuthenticationDto;
import com.GlamourByNora.api.exception.exceptionHandler.UserNotLoggedInException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface AppSecurityService {
    public ResponseEntity<?> login(AuthenticationDto authenticationDto, HttpServletResponse response) throws UserNotLoggedInException;
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) throws UserNotLoggedInException;
    public ResponseEntity<?> getLoggedInUser(HttpServletRequest request) throws UserNotLoggedInException;
}
