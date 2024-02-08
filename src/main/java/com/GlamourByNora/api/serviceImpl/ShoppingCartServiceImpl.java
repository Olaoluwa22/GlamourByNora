package com.GlamourByNora.api.serviceImpl;

import com.GlamourByNora.api.constants.ConstantMessages;
import com.GlamourByNora.api.dto.CartRequestDto;
import com.GlamourByNora.api.model.Cart;
import com.GlamourByNora.api.model.Product;
import com.GlamourByNora.api.model.User;
import com.GlamourByNora.api.repository.CartRepository;
import com.GlamourByNora.api.repository.ProductRepository;
import com.GlamourByNora.api.repository.UserRepository;
import com.GlamourByNora.api.response.ApiResponseMessages;
import com.GlamourByNora.api.service.AppSecurityService;
import com.GlamourByNora.api.service.ShoppingCartService;
import com.GlamourByNora.api.util.CartItems;
import com.GlamourByNora.api.util.GetCookieValue;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    @Autowired
    private AppSecurityService appSecurityService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;
    @Value("${app.cookie.login}")
    private String loginCookieName;
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
    @Override
    public ResponseEntity<?> purchase(HttpServletRequest request) {
        appSecurityService.getLoggedInUser(request);
        ApiResponseMessages<String> apiResponseMessages = new ApiResponseMessages<>();
        apiResponseMessages.setMessage(ConstantMessages.FAILED.getMessage());
        GetCookieValue cookie = new GetCookieValue();
        try {
            Optional<User> databaseUser = userRepository.findByEmail("SteveNora01@gmail.com");
            if (databaseUser.isEmpty()) {
                apiResponseMessages.setMessage(ConstantMessages.INVALID_EMAIL.getMessage());
                return new ResponseEntity<>(apiResponseMessages, HttpStatus.BAD_REQUEST);
            }
            User user = databaseUser.get();
            HttpSession session = request.getSession(false);
            Cart cart = new Cart();
            cart.setUser(user);
            List<CartItems> cartItems = (List<CartItems>) session.getAttribute("cart");
            List<Product> productsFromCartItems = new ArrayList<>();
            for (int i = 0; i < cartItems.size() ; i++) {
                if (!cartItems.isEmpty()) {
                    Product databaseProduct = productRepository.findProductById(cartItems.get(i).getProductId());
                    productsFromCartItems.add(databaseProduct);
                }
            }
            cart.setProducts(productsFromCartItems);
            cartRepository.save(cart);
            apiResponseMessages.setMessage(ConstantMessages.PROCEED.getMessage());
            return new ResponseEntity<>(apiResponseMessages, HttpStatus.OK);
        }catch (NullPointerException exception){
            exception.printStackTrace();
        }
        return new ResponseEntity<>(apiResponseMessages, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    private HttpSession generateNewCart(CartRequestDto cartRequestDto, HttpServletRequest request) {
        HttpSession session = request.getSession();
        List<CartItems> cartItems = new ArrayList<>(List.of(new CartItems(cartRequestDto.getProductId(), 1)));
        session.setAttribute("cart", cartItems);
        return session;
    }
}