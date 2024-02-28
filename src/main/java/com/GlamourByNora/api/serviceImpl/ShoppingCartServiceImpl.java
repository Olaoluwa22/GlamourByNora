package com.GlamourByNora.api.serviceImpl;

import com.GlamourByNora.api.InitializeTransaction.PaystackTransactionRequest;
import com.GlamourByNora.api.InitializeTransaction.PaystackTransactionResponse;
import com.GlamourByNora.api.dto.CartRequestDto;
import com.GlamourByNora.api.exception.exceptionHandler.NotAuthorizedException;
import com.GlamourByNora.api.exception.exceptionHandler.UserNotFoundException;
import com.GlamourByNora.api.model.Order;
import com.GlamourByNora.api.model.Product;
import com.GlamourByNora.api.model.User;
import com.GlamourByNora.api.repository.CartRepository;
import com.GlamourByNora.api.repository.OrderRepository;
import com.GlamourByNora.api.repository.ProductRepository;
import com.GlamourByNora.api.repository.UserRepository;
import com.GlamourByNora.api.response.ApiResponseMessages;
import com.GlamourByNora.api.service.AppSecurityService;
import com.GlamourByNora.api.service.EmailVerificationService;
import com.GlamourByNora.api.service.ShoppingCartService;
import com.GlamourByNora.api.util.CartItems;
import com.GlamourByNora.api.util.ConstantMessages;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RestControllerAdvice
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private static final int STATUS_CODE_OK = 200;
    @Autowired
    private AppSecurityService appSecurityService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private EmailVerificationService emailVerificationService;
    @Autowired
    private OrderRepository orderRepository;
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
                    session.setAttribute("cart", cart);
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
    public ResponseEntity<?> checkout(HttpServletRequest request) {
        appSecurityService.getLoggedInUser(request);
        getTotalValue(request);
        ApiResponseMessages<String> apiResponseMessages = new ApiResponseMessages<>();
        apiResponseMessages.setMessage(ConstantMessages.TOTAL_VALUE.getMessage());
        apiResponseMessages.setData(String.valueOf(getTotalValue(request)));
        return new ResponseEntity<>(apiResponseMessages, HttpStatus.OK);
    }
    @Override
    @Transactional(rollbackOn = {NullPointerException.class, NotAuthorizedException.class})
    public ResponseEntity<?> proceedToPayment(HttpServletRequest request, PaystackTransactionRequest paystackTransactionRequest) throws NotAuthorizedException {
        appSecurityService.getLoggedInUser(request);
        ApiResponseMessages<String> apiResponseMessages = new ApiResponseMessages<>();
        apiResponseMessages.setMessage(ConstantMessages.FAILED.getMessage());
        Cookie[] cookie = request.getCookies();
        Cookie value = null;
        for (int i = 0; i < cookie.length; i++) {
            Cookie cookies = cookie[i];
            if (cookies.getName().equalsIgnoreCase(loginCookieName)) {
                value = cookies;
                break;
            }
        }
        Optional<User> databaseUser = userRepository.findByEmail(value.getValue());
        if (databaseUser.isEmpty()){
            return new ResponseEntity<>(apiResponseMessages, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        User user = databaseUser.get();
        try {
            createOrder(request, user);
            initializeTransaction(request, paystackTransactionRequest, user);
            logTransaction(request, user.getId());
            updateInventory(request, user.getId());
            emailVerificationService.sendOrderConfirmationMail(user.getEmail(), "1234567");
        }
        catch (NullPointerException e){
            throw new NullPointerException("Cannot be Null");
        }catch(NotAuthorizedException | Exception e){
            throw new NotAuthorizedException("Error Occurred while initializing transaction");
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }
    private void createOrder(HttpServletRequest request, User user) throws NullPointerException{
        ApiResponseMessages<String> apiResponseMessages = new ApiResponseMessages<>();
        HttpSession session = request.getSession(false);
        List<CartItems> cartItems = (List<CartItems>) session.getAttribute("cart");
        if(cartItems.isEmpty()){
            throw new NullPointerException();
        }
        Order order = new Order();
        String orderNumber = generateRandomPrefix() + "-" + generateOrderId();
        order.setOrderNumber(orderNumber);
        order.setQuantityOfProduct(cartItems.size());
        order.setStatus(apiResponseMessages.setMessage(ConstantMessages.PROCESSING.getMessage()));
        order.setValue(getTotalValue(request));
        order.setUser(user);

        orderRepository.save(order);
    }
    private PaystackTransactionResponse initializeTransaction(HttpServletRequest request, PaystackTransactionRequest paystackTransactionRequest, User user) throws Exception, NotAuthorizedException {
        PaystackTransactionResponse paystackTransactionResponse = null;
        Optional<Order> databaseOrder = orderRepository.findOrderByUserId(user.getId());
        if (databaseOrder.isEmpty() || databaseOrder.get().getOrderNumber().equalsIgnoreCase("Delivered")){
            throw new UserNotFoundException("Order Number does not exist");
        }
        Order order = databaseOrder.get();
        paystackTransactionRequest.setAmount(order.getValue()*100);
        paystackTransactionRequest.setEmail(user.getEmail());
        paystackTransactionRequest.setReference(order.getOrderNumber());
        try {
            Gson gson = new Gson();
            StringEntity postingString = new StringEntity(gson.toJson(paystackTransactionRequest));
            CloseableHttpClient client = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost("https://api.paystack.co/transaction/initialize");
            post.setEntity(postingString);
            post.addHeader("Content-type", "application/json");
            post.addHeader("Authorization", "Bearer sk_test_b5756e19ed7f96c84b253095358de89a137f8252");
            StringBuilder result = new StringBuilder();
            CloseableHttpResponse response = client.execute(post);
            if (response.getStatusLine().getStatusCode() == STATUS_CODE_OK) {
                BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

                String line;
                while ((line = rd.readLine()) != null) {
                    result.append(line);
                }

            } else {
                throw new NotAuthorizedException("Error Occurred while initializing transaction");
            }
            ObjectMapper mapper = new ObjectMapper();

            paystackTransactionResponse = mapper.readValue(result.toString(), PaystackTransactionResponse.class);

        } catch (NotAuthorizedException e) {
            throw new NotAuthorizedException("Error Occurred while initializing transaction");

        } catch (Exception ex) {
            throw new Exception("Failure initializaing paystack transaction");
        }
        return paystackTransactionResponse;
    }
    private ResponseEntity<?> logTransaction(HttpServletRequest request, Long userId){
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    private ResponseEntity<?> updateInventory(HttpServletRequest request, Long userId){
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
    private void generateNewCart(CartRequestDto cartRequestDto, HttpServletRequest request) {
        HttpSession session = request.getSession();
        List<CartItems> cartItems = new ArrayList<>(List.of(new CartItems(cartRequestDto.getProductId(), 1)));
        session.setAttribute("cart", cartItems);
    }
    private int getTotalValue(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        List<CartItems> cartItems = (List<CartItems>) session.getAttribute("cart");
        int totalValue = 0;
        for (int i = 0; i < cartItems.size(); i++) {
            Optional<Product> databaseProduct = productRepository.findProductById(cartItems.get(i).getProductId());
            totalValue += (databaseProduct.get().getPrice() * cartItems.get(i).getQuantity());
        }
        return totalValue;
    }
    private static String generateRandomPrefix() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        Random random = new Random();
        StringBuilder prefix = new StringBuilder();
        for (int i = 0; i < 2; i++) {
            prefix.append(characters.charAt(random.nextInt(characters.length())));
        }
        return prefix.toString();
    }
    private static String generateOrderId() {
        Random random = new Random();
        String randomNumber = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 999999));
        return randomNumber;
    }
}
