package com.GlamourByNora.api.controller;

import com.GlamourByNora.api.dto.CartRequestDto;
import com.GlamourByNora.api.model.Product;
import com.GlamourByNora.api.model.User;
import com.GlamourByNora.api.service.ShoppingCartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ShoppingCartController {
    @Autowired
    private ShoppingCartService shoppingCartService;

    @PostMapping("/products/add-to-cart")
    public ResponseEntity<?> addToCart(@Valid @RequestBody CartRequestDto cartRequestDto, HttpServletRequest request){
        return shoppingCartService.addToCart(cartRequestDto, request);
    }
    @RequestMapping("/products/delete-from-cart")
    public ResponseEntity<?> deleteFromCart(){
        return shoppingCartService.deleteFromCart();
    }
}
