package com.GlamourByNora.api.serviceImpl;

import com.GlamourByNora.api.dto.CartRequestDto;
import com.GlamourByNora.api.repository.CartRepository;
import com.GlamourByNora.api.response.ApiResponseMessages;
import com.GlamourByNora.api.service.ShoppingCartService;
import com.GlamourByNora.api.util.CartItems;
import com.GlamourByNora.api.util.ConstantMessages;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;
@Service
@RestControllerAdvice
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private CartRepository cartRepository;
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
                  currentItem.setQuantity(currentItem.getQuantity()+1);
                  itemAlreadyExist = true;
                  break;
                }
            }
            if (!itemAlreadyExist){
                cartItems.add(new CartItems(cartRequestDto.getProductId(),1));
            }
            session.setAttribute("cart", cartItems);
            System.out.println(cartItems);
            apiResponseMessages.setMessage(ConstantMessages.ADDED_TO_CART.getMessage());
            return new ResponseEntity<>(apiResponseMessages, HttpStatus.OK);
        }
        if ((session != null) && session.getAttribute("cart") == null){
            generateNewCart(cartRequestDto, request);
            apiResponseMessages.setMessage(ConstantMessages.ADDED_TO_CART.getMessage());
            return new ResponseEntity<>(apiResponseMessages, HttpStatus.OK);
        }
        if (session == null){
            generateNewCart(cartRequestDto, request);
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
            List<CartItems> listOfItemsInCart = (List<CartItems>) session.getAttribute("cart");
            for (int i = 0; i < listOfItemsInCart.size() ; i++) {
                CartItems currentCartItem = listOfItemsInCart.get(i);
                if (currentCartItem.getProductId() == cartRequestDto.getProductId()){
                    listOfItemsInCart.remove(currentCartItem);
                    session.setAttribute("cart", listOfItemsInCart);
                    break;
                }
            }
            apiResponseMessages.setMessage(ConstantMessages.DELETED_FROM_CART.getMessage());
            return new ResponseEntity<>(apiResponseMessages, HttpStatus.OK);
        }
        apiResponseMessages.setMessage(ConstantMessages.FAILED.getMessage());
        return new ResponseEntity<>(apiResponseMessages, HttpStatus.BAD_REQUEST);
    }
     private void generateNewCart(CartRequestDto cartRequestDto, HttpServletRequest request) {
        HttpSession session = request.getSession();
        List<CartItems> cartItems = new ArrayList<>(List.of(new CartItems(cartRequestDto.getProductId(), 1)));
        session.setAttribute("cart", cartItems);
    }
}
