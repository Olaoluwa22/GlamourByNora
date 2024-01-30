package com.GlamourByNora.api.service;

import com.GlamourByNora.api.dto.PasswordDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface PasswordService {
    public ResponseEntity<?> updatePassword(PasswordDto passwordDto, HttpServletRequest request);
}
