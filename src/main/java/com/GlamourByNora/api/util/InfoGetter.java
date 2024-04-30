package com.GlamourByNora.api.util;

import com.GlamourByNora.api.exception.exceptionHandler.OrderNotFoundException;
import com.GlamourByNora.api.exception.exceptionHandler.ProductNotFoundException;
import com.GlamourByNora.api.exception.exceptionHandler.RequestedListIsEmptyException;
import com.GlamourByNora.api.exception.exceptionHandler.UserNotFoundException;
import com.GlamourByNora.api.model.OTP;
import com.GlamourByNora.api.model.Order;
import com.GlamourByNora.api.model.Product;
import com.GlamourByNora.api.model.User;
import com.GlamourByNora.api.repository.OTPRepository;
import com.GlamourByNora.api.repository.OrderRepository;
import com.GlamourByNora.api.repository.ProductRepository;
import com.GlamourByNora.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class InfoGetter {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OTPRepository otpRepository;
    public User getUser(String username){
        Optional<User> optionalUser = userRepository.findUserByEmail(username);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("Username not found");
        }
        return optionalUser.get();
    }
    public User getUserById(Long userId){
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User not found");
        }
        return optionalUser.get();
    }
    public User getUserByEmailAndDeleted(String username, boolean isDeleted){
        Optional<User> optionalUser = userRepository.findUserByEmailAndDeleted(username, false);
        if (optionalUser.isEmpty()) {
            throw new BadCredentialsException("Username or Password incorrect");
        }
        return optionalUser.get();
    }
    public User getUserByIdAndDeleted(Long userId, boolean isDeleted){
        Optional<User> optionalUser = userRepository.findUserByIdAndDeleted(userId, false);
        if (optionalUser.isEmpty()) {
            throw new BadCredentialsException("No user found");
        }
        if (optionalUser.get().isDeleted()) {
            throw new BadCredentialsException("This is already a account...");
        }
        return optionalUser.get();
    }
    public Order getOrder(Long userId){
        Optional<Order> optionalOrder = orderRepository.findByUserId(userId);
        if (optionalOrder.isEmpty() || !optionalOrder.get().getStatus().equalsIgnoreCase("Processing")){
            throw new OrderNotFoundException("Order not found");
        }
        return optionalOrder.get();
    }
    public Order getOrderByReference(String reference){
        Optional<Order> optionalOrder = orderRepository.findOrderByReference(reference);
        if (optionalOrder.isEmpty()){
            throw new OrderNotFoundException("Order not found");
        }
        return optionalOrder.get();
    }
    public Product getProduct(Long productId){
        Optional<Product> optionalProduct = productRepository.findProductById(productId);
        if (optionalProduct.isEmpty()){
            try {
                throw new ProductNotFoundException("Product not found");
            } catch (ProductNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        return optionalProduct.get();
    }
    public OTP getOtp(String otp){
        Optional<OTP> optionalOTP = otpRepository.findByOtp(otp);
        if (optionalOTP.isEmpty()){
            throw new BadCredentialsException("OTP not correct");
        }
        return optionalOTP.get();
    }
    public List<Order> getOrderByStatus(String status){
        Optional<List<Order>> optionalOrderList = orderRepository.findOrderByStatus(status);
        if (optionalOrderList.isEmpty()){
            try {
                throw new RequestedListIsEmptyException("No orders in "+status);
            } catch (RequestedListIsEmptyException e) {
                throw new RuntimeException(e);
            }
        }
        return optionalOrderList.get();
    }
}