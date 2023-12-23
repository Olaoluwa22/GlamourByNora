package com.GlamourByNora.api.service;

import com.GlamourByNora.api.exception.exceptionHandler.UserNotFoundException;
import com.GlamourByNora.api.exception.exceptionHandler.UserNotLoggedInException;
import com.GlamourByNora.api.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AppSecurityService {
    void login(User user, HttpServletRequest request, HttpServletResponse response) throws UserNotLoggedInException, UserNotFoundException;
    public void logout(HttpServletRequest request, HttpServletResponse response) throws UserNotLoggedInException;
    public void getLoggedInUser(HttpServletRequest request) throws UserNotLoggedInException;
}