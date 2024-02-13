package com.GlamourByNora.api.serviceImpl;

import com.GlamourByNora.api.constants.ConstantMessages;
import com.GlamourByNora.api.constants.ConstantMethod;
import com.GlamourByNora.api.dto.AuthenticationDto;
import com.GlamourByNora.api.dto.UserResponseDto;
import com.GlamourByNora.api.model.User;
import com.GlamourByNora.api.repository.UserRepository;
import com.GlamourByNora.api.response.ApiResponseMessages;
import com.GlamourByNora.api.service.AppSecurityService;
import com.GlamourByNora.api.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private UserRepository userRepository;
    private AppSecurityService appSecurityService;
    private ConstantMethod constantMethod;
    public AuthenticationServiceImpl(UserRepository userRepository, AppSecurityService appSecurityService, ConstantMethod constantMethod) {
        this.userRepository = userRepository;
        this.appSecurityService = appSecurityService;
        this.constantMethod = constantMethod;
    }
    @Override
    public ResponseEntity<?> login(AuthenticationDto authenticationDto, HttpServletResponse response, HttpServletRequest request) {
        ApiResponseMessages<String> apiResponseMessages = new ApiResponseMessages<>();
        apiResponseMessages.setMessage(ConstantMessages.FAILED.getMessage());
        try {
            Optional<User> byEmail = userRepository.findByEmailAndDeleted(authenticationDto.getUsername(), false);
            if (byEmail.isEmpty()) {
                apiResponseMessages.setMessage(ConstantMessages.INCORRECT.getMessage());
                return new ResponseEntity<>(apiResponseMessages, HttpStatus.BAD_REQUEST);
            }
            User databaseUser = byEmail.get();
            if (!authenticationDto.getPassword().equals(databaseUser.getPassword())) {
                apiResponseMessages.setMessage(ConstantMessages.INCORRECT.getMessage());
                return new ResponseEntity<>(apiResponseMessages, HttpStatus.BAD_REQUEST);
            }
            appSecurityService.login(databaseUser, request,response);
            apiResponseMessages.setMessage(ConstantMessages.USER_LOGGED_IN_SUCCESSFULLY.getMessage());
            return new ResponseEntity<>(apiResponseMessages, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(apiResponseMessages, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @Override
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        ApiResponseMessages<String> apiResponseMessages = new ApiResponseMessages<>();
        apiResponseMessages.setMessage(ConstantMessages.FAILED.getMessage());
        appSecurityService.getLoggedInUser(request);
        try {
            appSecurityService.logout(request, response);
            apiResponseMessages.setMessage(ConstantMessages.LOGGED_OUT.getMessage());
            return new ResponseEntity<>(apiResponseMessages, HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(apiResponseMessages, HttpStatus.INTERNAL_SERVER_ERROR);
        }
}