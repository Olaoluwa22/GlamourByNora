package com.GlamourByNora.api.controller;

import com.GlamourByNora.api.dto.AuthenticationDto;
import com.GlamourByNora.api.dto.SignupRequestDto;
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
    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignupRequestDto signupRequestDto){
        return authenticationService.signup(signupRequestDto);
    }
    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody AuthenticationDto authenticationDto, HttpServletResponse response, HttpServletRequest request){
        return authenticationService.login(authenticationDto, response, request);
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request){
         return authenticationService.logout(request);
    }
}















