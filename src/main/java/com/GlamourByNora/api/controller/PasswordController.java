package com.GlamourByNora.api.controller;

import com.GlamourByNora.api.dto.ForgetPasswordDto;
import com.GlamourByNora.api.dto.NewPasswordDto;
import com.GlamourByNora.api.dto.PasswordDto;
import com.GlamourByNora.api.dto.VerificationCodeDto;
import com.GlamourByNora.api.service.PasswordService;
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
@RequestMapping("/account")
public class PasswordController {
    @Autowired
    private PasswordService passwordService;

    @PostMapping("/update-password")
    public ResponseEntity<?> updatePassword(@Valid @RequestBody PasswordDto passwordDto, HttpServletRequest request){
        return passwordService.updatePassword(passwordDto, request);
    }
    @PostMapping("/forget-password")
    public ResponseEntity<?> forgetPassword(@Valid @RequestBody ForgetPasswordDto forgetPasswordDto, HttpServletResponse response){
        return passwordService.forgetPasswordAndSendOtp(forgetPasswordDto, response);
    }
    @PostMapping("/verify-code")
    public ResponseEntity<?> verifyCode(@Valid @RequestBody VerificationCodeDto verificationCodeDto, HttpServletRequest request){
        return passwordService.verifyOtp(verificationCodeDto, request);
    }
    @PostMapping("/resend-code")
    public ResponseEntity<?> resendCode(HttpServletRequest request){
        return passwordService.resendCode(request);
    }
    @PostMapping("/new-password")
    public ResponseEntity<?> setNewPassword(@Valid @RequestBody NewPasswordDto newPasswordDto, HttpServletRequest request){
        return passwordService.setNewPassword(newPasswordDto, request);
    }
}