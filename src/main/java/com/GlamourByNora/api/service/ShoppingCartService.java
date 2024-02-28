package com.GlamourByNora.api.service;

import com.GlamourByNora.api.InitializeTransaction.PaystackTransactionRequest;
import com.GlamourByNora.api.dto.CartRequestDto;
import com.GlamourByNora.api.exception.exceptionHandler.NotAuthorizedException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface ShoppingCartService {
     ResponseEntity<?> addToCart(CartRequestDto cartRequestDto, HttpServletRequest request);
     ResponseEntity<?> deleteFromCart(CartRequestDto cartRequestDto, HttpServletRequest request);
     ResponseEntity<?> checkout(HttpServletRequest request);
     ResponseEntity<?> proceedToPayment(HttpServletRequest request, PaystackTransactionRequest paystackTransactionRequest) throws NotAuthorizedException;
}
