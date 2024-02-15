package com.GlamourByNora.api.service;

import com.GlamourByNora.api.model.User;

public interface EmailVerificationService {
    public void sendVerificationCode(String email, String verificationCode);
    public void sendOrderConfirmationMail(String email, String confirmationText);
}
