package com.GlamourByNora.api.controller;

import com.GlamourByNora.api.InitializeTransaction.PaystackTransactionRequest;
import com.GlamourByNora.api.dto.CartRequestDto;
import com.GlamourByNora.api.exception.exceptionHandler.NotAuthorizedException;
import com.GlamourByNora.api.service.ShoppingCartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
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
}