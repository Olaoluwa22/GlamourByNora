package com.GlamourByNora.api.controller;

import com.GlamourByNora.api.dto.PaystackTransactionRequestDto;
import com.GlamourByNora.api.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(HttpServletRequest request){
        return paymentService.checkout(request);
    }
    @PostMapping("/proceed-to-payment")
    public ResponseEntity<?> proceedToPayment(HttpServletRequest request, PaystackTransactionRequestDto paystackTransactionRequestDto) throws IOException {
        return paymentService.proceedToPayment(request, paystackTransactionRequestDto);
    }
    @GetMapping("/verify-payment")
    public ResponseEntity<?> verifyPaystackTransaction(HttpServletRequest request) throws InterruptedException {
        return paymentService.verifyPaystackTransaction(request);
    }
}