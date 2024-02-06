package com.GlamourByNora.api.service;

import com.GlamourByNora.api.model.User;

public interface EmailVerificationService {
    public void sendVerificationCode(User user, String verificationCode);
}
