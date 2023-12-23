package com.GlamourByNora.api.service;

import com.GlamourByNora.api.exception.exceptionHandler.UserNotLoggedInException;
import com.GlamourByNora.api.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface SessionAuthenticationService {
    public void login(User user, HttpServletRequest request);
    public void logout(HttpServletRequest request, HttpServletResponse response);
    public void isUserLoggedIn(HttpServletRequest request) throws UserNotLoggedInException;
}