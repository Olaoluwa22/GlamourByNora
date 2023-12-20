package com.GlamourByNora.api.serviceImpl;

import com.GlamourByNora.api.constants.ConstantMessages;
import com.GlamourByNora.api.constants.ConstantMethods;
import com.GlamourByNora.api.dto.AuthenticationDto;
import com.GlamourByNora.api.model.User;
import com.GlamourByNora.api.repository.UserRepository;
import com.GlamourByNora.api.response.ApiResponseMessages;
import com.GlamourByNora.api.service.AppSecurityService;
import com.GlamourByNora.api.service.AuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AppSecurityService appSecurityService;

    @Override
    public ResponseEntity<?> login(AuthenticationDto authenticationDto, HttpServletResponse response) {
        ApiResponseMessages<String> apiResponseMessages = new ApiResponseMessages<>();
        apiResponseMessages.setMessage(ConstantMessages.FAILED.getMessage());
        try {
            Optional<User> byEmail = userRepository.findByEmail(authenticationDto.getUsername());
            if (byEmail.isEmpty()) {
                apiResponseMessages.setMessage(ConstantMessages.INCORRECT.getMessage());
                return new ResponseEntity<>(apiResponseMessages, HttpStatus.BAD_REQUEST);
            }
            User user = byEmail.get();
            if (user.isDeleted()) {
                apiResponseMessages.setMessage(ConstantMessages.INCORRECT.getMessage());
                return new ResponseEntity<>(apiResponseMessages, HttpStatus.BAD_REQUEST);
            }

            if (!authenticationDto.getPassword().equals(user.getPassword())) {
                apiResponseMessages.setMessage(ConstantMessages.INCORRECT.getMessage());
                return new ResponseEntity<>(apiResponseMessages, HttpStatus.BAD_REQUEST);
            }

            Cookie cookie = new Cookie("login", authenticationDto.getUsername());
            cookie.setMaxAge(600);
            cookie.setHttpOnly(true);
            response.addCookie(cookie);
            apiResponseMessages.setMessage(ConstantMessages.SUCCESS.getMessage());
            return new ResponseEntity<>(apiResponseMessages, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(apiResponseMessages, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @Override
    public ResponseEntity<?> logout() {
        return null;
    }

    @Override
    public ResponseEntity<?> isUserLoggedIn(HttpServletRequest request) throws Exception {
        return appSecurityService.getLoggedInUser(request);
    }

}