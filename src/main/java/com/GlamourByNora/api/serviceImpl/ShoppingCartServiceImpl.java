package com.GlamourByNora.api.serviceImpl;

import com.GlamourByNora.api.constants.CartItems;
import com.GlamourByNora.api.constants.ConstantMessages;
import com.GlamourByNora.api.dto.CartRequestDto;
import com.GlamourByNora.api.model.Product;
import com.GlamourByNora.api.model.User;
import com.GlamourByNora.api.repository.CartRepository;
import com.GlamourByNora.api.repository.ProductRepository;
import com.GlamourByNora.api.response.ApiResponseMessages;
import com.GlamourByNora.api.service.ShoppingCartService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private ProductRepository productRepository;
    private CartRepository cartRepository;
    private CartItems cartItems;
    public ShoppingCartServiceImpl(ProductRepository productRepository, CartRepository cartRepository, CartItems cartItems){
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartItems = cartItems;
    }
    @Override
    public ResponseEntity<?> addToCart(CartRequestDto cartRequestDto, HttpServletRequest request) {
        ApiResponseMessages<String> apiResponseMessages = new ApiResponseMessages<>();

        HttpSession session = request.getSession(false);
        if(session != null && session.getAttribute("cart")!= null){
            List<CartItems> cartItems = (List<CartItems>) session.getAttribute("cart");
            boolean itemAlreadyExist = false;
            for (int i = 0; i < cartItems.size(); i++) {
                CartItems currentItem = cartItems.get(i);
                if(currentItem.getProduct().getId() == cartRequestDto.getProductId()){
                  cartItems.get(i).setQuantity(currentItem.getQuantity()+1);
                  itemAlreadyExist = true;
                  break;
                }
            }
            if (!itemAlreadyExist){
                cartItems.add(new CartItems(cartRequestDto.getProductId(),1));
            }
            session.setAttribute("cart", cartItems);
            System.out.println(cartItems);
        }
        if ((session != null) && session.getAttribute("cart") == null){
            session = generateNewCart(cartRequestDto, request);
        }
        if (session == null){
            
        }
        apiResponseMessages.setMessage(ConstantMessages.SUCCESS.getMessage());
        return new ResponseEntity<>(apiResponseMessages, HttpStatus.OK);
    }

    private HttpSession generateNewCart(CartRequestDto cartRequestDto, HttpServletRequest request) {
        HttpSession session = request.getSession();
        List<CartItems> cartItems = cartRequestDto.getProductId();
        session.setAttribute("cart", cartItems);
        return session;
    }

    @Override
    public ResponseEntity<?> deleteFromCart() {
        return null;
    }
}
