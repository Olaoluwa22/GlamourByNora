package com.GlamourByNora.api.controller;

import com.GlamourByNora.api.dto.AuthenticationDto;
import com.GlamourByNora.api.model.User;
import com.GlamourByNora.api.service.AuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    private AuthenticationService authenticationService;
    public AuthenticationController(AuthenticationService authenticationService){
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthenticationDto authenticationDto, HttpServletResponse response, HttpServletRequest request){
        return authenticationService.login(authenticationDto, response, request);
    }
    @PostMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response){
         authenticationService.logout(request, response);
    }
}