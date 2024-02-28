package com.GlamourByNora.api.serviceImpl;

import com.GlamourByNora.api.util.ConstantMessages;
import com.GlamourByNora.api.exception.exceptionHandler.UserNotFoundException;
import com.GlamourByNora.api.exception.exceptionHandler.UserNotLoggedInException;
import com.GlamourByNora.api.model.User;
import com.GlamourByNora.api.response.ApiResponseMessages;
import com.GlamourByNora.api.service.AppSecurityService;
import com.GlamourByNora.api.service.CookieAuthenticationService;
import com.GlamourByNora.api.service.SessionAuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AppSecurityServiceImpl implements AppSecurityService {

    private CookieAuthenticationService cookieAuthenticationService;
    private SessionAuthenticationService sessionAuthenticationService;
    public AppSecurityServiceImpl(CookieAuthenticationService cookieAuthenticationService, SessionAuthenticationService sessionAuthenticationService){
        this.cookieAuthenticationService = cookieAuthenticationService;
        this.sessionAuthenticationService = sessionAuthenticationService;
    }

    @Value("${app.authType}")
    private String authType;
    @Override
    public void login(User user, HttpServletRequest request, HttpServletResponse response) throws UserNotFoundException {
        if (authType.equalsIgnoreCase("Cookie")) {
            cookieAuthenticationService.login(user, response);
        } else if (authType.equalsIgnoreCase("session"))
            sessionAuthenticationService.login(user, request);
        else {
            throw new UserNotFoundException("No Login Mechanism");
        }
    }
    @Override
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) throws UserNotLoggedInException {
        if (authType.equalsIgnoreCase("cookie")) {
            cookieAuthenticationService.logout(request, response);
        } else if (authType.equalsIgnoreCase("session")) {
            sessionAuthenticationService.logout(request, response);
        } else {
            throw new UserNotLoggedInException("User not Logged In");
        }
        ApiResponseMessages<String> apiResponseMessage = new ApiResponseMessages<>();
        apiResponseMessage.setMessage(ConstantMessages.LOGGED_OUT.getMessage());
        return new ResponseEntity<>(apiResponseMessage, HttpStatus.OK);
    }
    @Override
    public void getLoggedInUser(HttpServletRequest request) throws UserNotLoggedInException {
        if (authType.equalsIgnoreCase("cookie")) {
            cookieAuthenticationService.getLoggedInUser(request);
        }
        else if (authType.equalsIgnoreCase("session")) {
            sessionAuthenticationService.isUserLoggedIn(request);
        } else {
            throw new UserNotLoggedInException("User not Logged In");
        }
    }
}
