package com.GlamourByNora.api.controller;

import com.GlamourByNora.api.dto.CartRequestDto;
import com.GlamourByNora.api.exception.exceptionHandler.NotAuthorizedException;
import com.GlamourByNora.api.paymentModel.PaystackTransactionRequest;
import com.GlamourByNora.api.service.ShoppingCartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;
    @PostMapping("/add-to-cart")
    public ResponseEntity<?> addToCart(@Valid @RequestBody CartRequestDto cartRequestDto, HttpServletRequest request){
        return shoppingCartService.addToCart(cartRequestDto, request);
    }
    @PostMapping("/delete-from-cart")
    public ResponseEntity<?> deleteFromCart(@RequestBody CartRequestDto cartRequestDto, HttpServletRequest request){
        return shoppingCartService.deleteFromCart(cartRequestDto, request);
    }
    @PostMapping("/checkout")
    public ResponseEntity<?> checkout(HttpServletRequest request){
        return shoppingCartService.checkout(request);
    }
    @PostMapping("/proceed-to-payment")
    public ResponseEntity<?> proceedToPayment(HttpServletRequest request, PaystackTransactionRequest paystackTransactionRequest) throws NotAuthorizedException {
        return shoppingCartService.proceedToPayment(request, paystackTransactionRequest);
    }
    @GetMapping("/verify-payment/{reference}")
    public ResponseEntity<?> verifyPaystackTransaction(@PathVariable String reference, HttpServletRequest request) throws InterruptedException {
        return shoppingCartService.verifyPaystackTransaction(reference, request);
    }
}