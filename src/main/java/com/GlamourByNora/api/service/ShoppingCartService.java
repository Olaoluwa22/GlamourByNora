package com.GlamourByNora.api.service;

import com.GlamourByNora.api.dto.CartRequestDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface ShoppingCartService {
     ResponseEntity<?> addToCart(CartRequestDto cartRequestDto, HttpServletRequest request);
     ResponseEntity<?> deleteFromCart(CartRequestDto cartRequestDto, HttpServletRequest request);
}
