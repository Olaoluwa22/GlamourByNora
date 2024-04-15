package com.GlamourByNora.api.service;

import com.GlamourByNora.api.dto.PaystackTransactionRequestDto;
import com.GlamourByNora.api.exception.exceptionHandler.NotAuthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface PaymentService {
    ResponseEntity<?> checkout(HttpServletRequest request);
    ResponseEntity<?> proceedToPayment(HttpServletRequest request, PaystackTransactionRequestDto paystackTransactionRequestDto) throws NotAuthorizedException;
    ResponseEntity<?> verifyPaystackTransaction(String reference, HttpServletRequest request) throws InterruptedException;
}
