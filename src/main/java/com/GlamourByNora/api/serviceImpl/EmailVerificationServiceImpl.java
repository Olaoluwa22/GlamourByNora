package com.GlamourByNora.api.serviceImpl;

import com.GlamourByNora.api.service.EmailVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailVerificationServiceImpl implements EmailVerificationService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Override
    public void sendOTP(String email, String otp) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject("OTP Verification");
        mailMessage.setFrom("GlamourByNora@gmail.com");
        mailMessage.setTo(email);
        mailMessage.setText(otp);
    try {
        javaMailSender.send(mailMessage);
    }catch (MailException mailException) {
        mailException.printStackTrace();
       }
    }
    @Override
    public void sendOrderConfirmationMail(String email, String confirmationMail) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject("CustomerOrder Confirmation");
        mailMessage.setFrom("GlamourByNora@gmail.com");
        mailMessage.setTo(email);
        mailMessage.setText(confirmationMail);
        try {
            javaMailSender.send(mailMessage);
        }catch (MailException mailException) {
            mailException.printStackTrace();
        }
    }
}