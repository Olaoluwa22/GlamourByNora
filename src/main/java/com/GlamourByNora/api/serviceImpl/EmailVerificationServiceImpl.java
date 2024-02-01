package com.GlamourByNora.api.serviceImpl;

import com.GlamourByNora.api.constants.ConstantMessages;
import com.GlamourByNora.api.model.User;
import com.GlamourByNora.api.response.ApiResponseMessages;
import com.GlamourByNora.api.service.EmailVerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailVerificationServiceImpl implements EmailVerificationService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Override
    public ResponseEntity<?> sendVerificationCode(User user, String verificationCode) {
        ApiResponseMessages<String> apiResponseMessages = new ApiResponseMessages<>();
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setSubject("Verification Code");
        mailMessage.setFrom("GlamourByNora@gmai.com");
        mailMessage.setTo(user.getEmail());
        mailMessage.setText(verificationCode);
        System.out.println(verificationCode);

        javaMailSender.send(mailMessage);
        apiResponseMessages.setMessage(ConstantMessages.SUCCESS.getMessage());
        return new ResponseEntity<>(apiResponseMessages, HttpStatus.OK);

    }
}
