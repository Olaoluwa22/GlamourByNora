package com.GlamourByNora.api.service;

import com.GlamourByNora.api.dto.PaystackTransactionRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface PaymentService {
    ResponseEntity<?> checkout(HttpServletRequest request);
    ResponseEntity<?> proceedToPayment(HttpServletRequest request, PaystackTransactionRequestDto paystackTransactionRequestDto) throws IOException;
    ResponseEntity<?> verifyPaystackTransaction(HttpServletRequest request) throws InterruptedException;
}
