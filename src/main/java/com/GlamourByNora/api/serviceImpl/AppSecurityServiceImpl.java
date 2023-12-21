package com.GlamourByNora.api.serviceImpl;

import com.GlamourByNora.api.constants.ConstantMessages;
import com.GlamourByNora.api.dto.AuthenticationDto;
import com.GlamourByNora.api.exception.exceptionHandler.UserNotLoggedInException;
import com.GlamourByNora.api.model.User;
import com.GlamourByNora.api.repository.UserRepository;
import com.GlamourByNora.api.response.ApiResponseMessages;
import com.GlamourByNora.api.service.AppSecurityService;
import com.GlamourByNora.api.service.CookieAuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppSecurityServiceImpl implements AppSecurityService {
    @Autowired
    private CookieAuthenticationService cookieAuthenticationService;

    @Override
    public ResponseEntity<?> login(AuthenticationDto authenticationDto, HttpServletResponse response) throws UserNotLoggedInException {
        return null;
    }

    @Override
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) throws UserNotLoggedInException {
        return null;
    }

    @Override
    public ResponseEntity<?> getLoggedInUser(HttpServletRequest request) throws UserNotLoggedInException {
        return cookieAuthenticationService.getLoggedInUser(request);
    }
}