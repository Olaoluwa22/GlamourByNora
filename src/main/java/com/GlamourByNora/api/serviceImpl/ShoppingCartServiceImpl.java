package com.GlamourByNora.api.serviceImpl;

import com.GlamourByNora.api.constants.CartItems;
import com.GlamourByNora.api.constants.ConstantMessages;
import com.GlamourByNora.api.dto.CartRequestDto;
import com.GlamourByNora.api.response.ApiResponseMessages;
import com.GlamourByNora.api.service.ShoppingCartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Override
    public ResponseEntity<?> addToCart(CartRequestDto cartRequestDto, HttpServletRequest request) {
        ApiResponseMessages<String> apiResponseMessages = new ApiResponseMessages<>();
        HttpSession session = request.getSession(false);
        if(session != null && session.getAttribute("cart")!= null){
            List<CartItems> cartItems = (List<CartItems>) session.getAttribute("cart");
            boolean itemAlreadyExist = false;
            for (int i = 0; i < cartItems.size(); i++) {
                CartItems currentItem = cartItems.get(i);
                if(currentItem.getProductId() == cartRequestDto.getProductId()){
                  cartItems.get(i).setQuantity(currentItem.getQuantity()+1);
                  itemAlreadyExist = true;
                  break;
                }
            }
            if (!itemAlreadyExist){
                cartItems.add(new CartItems(cartRequestDto.getProductId(),1));
                apiResponseMessages.setMessage(ConstantMessages.ADDED_TO_CART.getMessage());
                return new ResponseEntity<>(apiResponseMessages, HttpStatus.OK);
            }
            session.setAttribute("cart", cartItems);
            System.out.println(cartItems);
            apiResponseMessages.setMessage(ConstantMessages.ADDED_TO_CART.getMessage());
            return new ResponseEntity<>(apiResponseMessages, HttpStatus.OK);
        }
        if ((session != null) && session.getAttribute("cart") == null){
            session = generateNewCart(cartRequestDto, request);
            apiResponseMessages.setMessage(ConstantMessages.ADDED_TO_CART.getMessage());
            return new ResponseEntity<>(apiResponseMessages, HttpStatus.OK);
        }
        if (session == null){
            session = generateNewCart(cartRequestDto, request);
            apiResponseMessages.setMessage(ConstantMessages.ADDED_TO_CART.getMessage());
            return new ResponseEntity<>(apiResponseMessages, HttpStatus.OK);
        }
        apiResponseMessages.setMessage(ConstantMessages.FAILED.getMessage());
        return new ResponseEntity<>(apiResponseMessages, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @Override
    public ResponseEntity<?> deleteFromCart(CartRequestDto cartRequestDto, HttpServletRequest request) {
        ApiResponseMessages<String> apiResponseMessages = new ApiResponseMessages<>();
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("cart") != null){
            List<CartItems> cart = (List<CartItems>) session.getAttribute("cart");
            for (int i = 0; i < cart.size() ; i++) {
                CartItems currentCartItem = cart.get(i);
                if (currentCartItem.getProductId() == cartRequestDto.getProductId()){
                    cart.remove(currentCartItem);
                    break;
                }
            }
            apiResponseMessages.setMessage(ConstantMessages.DELETED_FROM_CART.getMessage());
            return new ResponseEntity<>(apiResponseMessages, HttpStatus.OK);
        }
        apiResponseMessages.setMessage(ConstantMessages.FAILED.getMessage());
        return new ResponseEntity<>(apiResponseMessages, HttpStatus.BAD_REQUEST);
    }
    private HttpSession generateNewCart(CartRequestDto cartRequestDto, HttpServletRequest request) {
        HttpSession session = request.getSession();
        List<CartItems> cartItems = new ArrayList<>(Arrays.asList(new CartItems(cartRequestDto.getProductId(), 1)));
        session.setAttribute("cart", cartItems);
        return session;
    }
}