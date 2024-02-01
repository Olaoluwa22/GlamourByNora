package com.GlamourByNora.api.service;

import com.GlamourByNora.api.controller.ForgetPasswordDto;
import com.GlamourByNora.api.dto.NewPasswordDto;
import com.GlamourByNora.api.dto.PasswordDto;
import com.GlamourByNora.api.dto.VerificationCodeDto;
import com.GlamourByNora.api.model.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface PasswordService {
    public ResponseEntity<?> updatePassword(PasswordDto passwordDto, HttpServletRequest request);
    public ResponseEntity<?> forgetPassword(ForgetPasswordDto forgetPasswordDto, HttpServletResponse response);
    public ResponseEntity<?> verifyCode(VerificationCodeDto verificationCodeDto);
    public ResponseEntity<?> setNewPassword(NewPasswordDto newPasswordDto, HttpServletRequest request);

}
