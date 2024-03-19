package com.GlamourByNora.api.service;

public interface EmailVerificationService {
    public void sendOTP(String email, String verificationCode);
    public void sendOrderConfirmationMail(String email, String confirmationText);
}
