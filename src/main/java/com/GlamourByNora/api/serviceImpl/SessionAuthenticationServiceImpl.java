package com.GlamourByNora.api.serviceImpl;

import com.GlamourByNora.api.dto.AuthenticationDto;
import com.GlamourByNora.api.exception.exceptionHandler.UserNotLoggedInException;
import com.GlamourByNora.api.service.SessionAuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SessionAuthenticationServiceImpl implements SessionAuthenticationService {
    @Override
    public ResponseEntity<?> login(AuthenticationDto authenticationDto, HttpServletResponse response) {
        return null;
    }

    @Override
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        return null;
    }

    @Override
    public ResponseEntity<?> isUserLoggedIn(HttpServletRequest request) throws UserNotLoggedInException {
        return null;
    }
}
