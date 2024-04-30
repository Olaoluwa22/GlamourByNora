package com.GlamourByNora.api.controller;

import com.GlamourByNora.api.dto.PaystackTransactionRequestDto;
import com.GlamourByNora.api.exception.exceptionHandler.NotAuthorizedException;
import com.GlamourByNora.api.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<?> proceedToPayment(HttpServletRequest request, PaystackTransactionRequestDto paystackTransactionRequestDto) throws NotAuthorizedException {
        return paymentService.proceedToPayment(request, paystackTransactionRequestDto);
    }
    @GetMapping("/verify-payment")
    public ResponseEntity<?> verifyPaystackTransaction(HttpServletRequest request) throws InterruptedException {
        return paymentService.verifyPaystackTransaction(request);
    }
}