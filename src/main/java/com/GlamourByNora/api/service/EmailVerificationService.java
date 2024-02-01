package com.GlamourByNora.api.service;

import com.GlamourByNora.api.model.User;
import org.springframework.http.ResponseEntity;

public interface EmailVerificationService {
    public ResponseEntity<?> sendVerificationCode(User user, String verificationCode);
}
