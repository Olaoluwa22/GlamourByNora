package com.GlamourByNora.api.service;

import com.GlamourByNora.api.dto.ForgetPasswordDto;
import com.GlamourByNora.api.dto.NewPasswordDto;
import com.GlamourByNora.api.dto.PasswordDto;
import com.GlamourByNora.api.dto.VerificationCodeDto;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface PasswordService {
    public ResponseEntity<?> updatePassword(PasswordDto passwordDto, HttpServletRequest request);
    public ResponseEntity<?> forgetPasswordAndSendOtp(ForgetPasswordDto forgetPasswordDto, HttpServletResponse response);
    public ResponseEntity<?> verifyOtp(VerificationCodeDto verificationCodeDto, HttpServletRequest request);
    public ResponseEntity<?> resendCode(HttpServletRequest request);
    public ResponseEntity<?> setNewPassword(NewPasswordDto newPasswordDto, HttpServletRequest request);

}
