package com.GlamourByNora.api.service;

import com.GlamourByNora.api.exception.exceptionHandler.UserNotFoundException;
import com.GlamourByNora.api.exception.exceptionHandler.UserNotLoggedInException;
import com.GlamourByNora.api.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface AppSecurityService {
    void login(User user, HttpServletRequest request, HttpServletResponse response) throws UserNotLoggedInException, UserNotFoundException;
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) throws UserNotLoggedInException;
    public void getLoggedInUser(HttpServletRequest request) throws UserNotLoggedInException;
}