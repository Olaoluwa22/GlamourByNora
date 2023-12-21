package com.GlamourByNora.api.service;

import com.GlamourByNora.api.exception.exceptionHandler.UserNotLoggedInException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface AppSecurityService {
    public ResponseEntity<?> getLoggedInUser(HttpServletRequest request) throws UserNotLoggedInException;
}
