package com.GlamourByNora.api.serviceImpl;

import com.GlamourByNora.api.dto.CartRequestDto;
import com.GlamourByNora.api.exception.exceptionHandler.NotAuthorizedException;
import com.GlamourByNora.api.exception.exceptionHandler.OrderNotFoundException;
import com.GlamourByNora.api.exception.exceptionHandler.UserNotFoundException;
import com.GlamourByNora.api.model.Order;
import com.GlamourByNora.api.model.Product;
import com.GlamourByNora.api.model.User;
import com.GlamourByNora.api.paymentModel.PaystackTransactionRequest;
import com.GlamourByNora.api.paymentModel.PaystackVerificationResponse;
import com.GlamourByNora.api.repository.CartRepository;
import com.GlamourByNora.api.repository.OrderRepository;
import com.GlamourByNora.api.repository.ProductRepository;
import com.GlamourByNora.api.repository.UserRepository;
import com.GlamourByNora.api.response.ApiResponseMessages;
import com.GlamourByNora.api.service.EmailVerificationService;
import com.GlamourByNora.api.service.ShoppingCartService;
import com.GlamourByNora.api.util.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
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
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
@RestControllerAdvice
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private static final int STATUS_CODE_OK = 200;
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
        ApiResponseMessages<String> apiResponseMessages = new ApiResponseMessages<>();
        apiResponseMessages.setMessage(ConstantMessages.TOTAL_VALUE.getMessage());
        apiResponseMessages.setData(String.valueOf(validateQuantityAndGetTotalValue(request)));
        return new ResponseEntity<>(apiResponseMessages, HttpStatus.OK);
    }
    @Override
    @Transactional(rollbackOn = {NullPointerException.class, NotAuthorizedException.class})
    public ResponseEntity<?> proceedToPayment(HttpServletRequest request, PaystackTransactionRequest paystackTransactionRequest) throws NotAuthorizedException {
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
        Optional<User> databaseUser = userRepository.findUserByEmail(value.getValue());
        if (databaseUser.isEmpty()) {
            return new ResponseEntity<>(apiResponseMessages, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        User user = databaseUser.get();
        String paymentUrl;
        try {
            createOrder(request, user);
            paymentUrl = initializeTransaction(paystackTransactionRequest, user);
        } catch (NullPointerException e) {
            throw new NullPointerException("Cannot be Null");
        } catch (Exception e) {
            throw new NotAuthorizedException("Error Occurred while initializing transaction");
        }
        return new ResponseEntity<>(paymentUrl, HttpStatus.OK);
    }
    @Override
    public ResponseEntity<?> verifyPaystackTransaction(String reference, HttpServletRequest httpRequest) throws InterruptedException {
      ApiResponseMessages<String> apiResponseMessages = new ApiResponseMessages<>();
      apiResponseMessages.setMessage(ConstantMessages.FAILED.getMessage());
      Cookie[] cookie = httpRequest.getCookies();
      Cookie value = null;
      for (int i = 0; i < cookie.length; i++) {
          Cookie cookies = cookie[i];
          if (cookies.getName().equalsIgnoreCase(loginCookieName)) {
              value = cookies;
              break;
          }
      }
      Optional<User> databaseUser = userRepository.findUserByEmail(value.getValue());
      if (databaseUser.isEmpty()) {
          return new ResponseEntity<>(apiResponseMessages, HttpStatus.INTERNAL_SERVER_ERROR);
      }
      User user = databaseUser.get();
        Optional<Order> databaseOrder = orderRepository.findOrderByUserId(user.getId());
        if (databaseOrder.isEmpty() || !databaseOrder.get().getStatus().equalsIgnoreCase("Processing")){
            return new ResponseEntity<>(apiResponseMessages, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        Order order = databaseOrder.get();
        PaystackVerificationResponse verificationResponse = null;
      try {
          CloseableHttpClient client = HttpClientBuilder.create().build();
          HttpGet request = new HttpGet("https://api.paystack.co/transaction/verify/" + reference);
          request.addHeader("Content-type", "application/json");
          request.addHeader("Authorization", "Bearer sk_test_b5756e19ed7f96c84b253095358de89a137f8252");
          StringBuilder result = new StringBuilder();
          CloseableHttpResponse response = client.execute(request);
          if (response.getStatusLine().getStatusCode() == STATUS_CODE_OK) {
              BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
              String line;
              while ((line = bufferedReader.readLine()) != null) {
                  result.append(line);
              }
          }
          else {
              throw new InterruptedException("Error occurred while connecting to paystack verification url");
          }
          ObjectMapper mapper = new ObjectMapper();
          verificationResponse = mapper.readValue(result.toString(), PaystackVerificationResponse.class);
          if (verificationResponse == null || verificationResponse.getStatus().equals("false")) {
              throw new InterruptedException("An error occurred while verifying payment");
          } else if (!verificationResponse.getPaystackVerificationData().getAmount().equals(String.valueOf(order.getValue()))) {
              throw new NumberFormatException("Amount paid does not equal order value");
          } else if (verificationResponse.getPaystackVerificationData().getStatus().equals("success")) {
              Update update = new Update();
              update.updateOrder(user, verificationResponse.getPaystackVerificationData().getTransaction_date());
              update.updateInventory(httpRequest);
              HttpSession session = httpRequest.getSession(false);
              session.invalidate();
              apiResponseMessages.setMessage(ConstantMessages.TRANSACTION_SUCCESSFUL.getMessage());
              return new ResponseEntity<>(apiResponseMessages, HttpStatus.OK);
          }
      } catch (InterruptedException | IOException | NumberFormatException | OrderNotFoundException ex) {
          throw new InterruptedException("Internal server error");
      }
      return new ResponseEntity<>(apiResponseMessages, HttpStatus.INTERNAL_SERVER_ERROR);
  }
    private void createOrder(HttpServletRequest request, User user) throws NullPointerException{
        ApiResponseMessages<String> apiResponseMessages = new ApiResponseMessages<>();
        HttpSession session = request.getSession(false);
        List<CartItems> cartItems = (List<CartItems>) session.getAttribute("cart");
        if(cartItems.isEmpty()){
            throw new NullPointerException();
        }
        Order order = new Order();
        order.setReference(new OrderReference().generateOrderReference());
        order.setQuantityOfProductsOrdered(cartItems.size());
        order.setStatus(apiResponseMessages.setMessage(ConstantMessages.PROCESSING.getMessage()));
        order.setValue(validateQuantityAndGetTotalValue(request));
        order.setUser(user);
        order.setCreatedAt(Instant.now());
        orderRepository.save(order);
    }
    private String initializeTransaction(PaystackTransactionRequest paystackTransactionRequest, User user) throws IOException, NotAuthorizedException {
        String paymentUrl = null;
        Optional<Order> databaseOrder = orderRepository.findOrderByUserId(user.getId());
        if (databaseOrder.isEmpty() || databaseOrder.get().getStatus().equals("Paid") || databaseOrder.get().getStatus().equals("Delivered")){
            throw new UserNotFoundException("Invalid Order reference");
        }
        Order order = databaseOrder.get();
        paystackTransactionRequest.setAmount(order.getValue()*100);
        paystackTransactionRequest.setEmail(user.getEmail());
        paystackTransactionRequest.setReference(order.getReference());
        paystackTransactionRequest.setCallback_url("http://localhost:8080/verify-payment/"+order.getReference());
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
                throw new NotAuthorizedException("Error Occurred in transaction process...");
            }
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(result.toString());
            paymentUrl = rootNode.get("data").get("authorization_url").asText();

        } catch (NotAuthorizedException e) {
            throw new NotAuthorizedException("Error Occurred while initializing transaction");

        } catch (IOException ex) {
            throw new IOException("Failure initializaing paystack transaction");
        }
        return paymentUrl;
    }
    private void generateNewCart(CartRequestDto cartRequestDto, HttpServletRequest request) {
        HttpSession session = request.getSession();
        List<CartItems> cartItems = new ArrayList<>(List.of(new CartItems(cartRequestDto.getProductId(), 1)));
        session.setAttribute("cart", cartItems);
    }
    private int validateQuantityAndGetTotalValue(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        List<CartItems> listOfCartItems = (List<CartItems>) session.getAttribute("cart");
        int totalValue = 0;
        for (int i = 0; i < listOfCartItems.size(); i++) {
            Optional<Product> databaseProduct = productRepository.findProductById(listOfCartItems.get(i).getProductId());
            if (databaseProduct.isEmpty()){
                throw new NullPointerException();
            }
            Product product = databaseProduct.get();
            if (product.getStockQuantity() == 0){
                listOfCartItems.remove(listOfCartItems.get(i));
            } else if ((product.getStockQuantity() < listOfCartItems.get(i).getQuantity())) {
                listOfCartItems.get(i).setQuantity(product.getStockQuantity());
                totalValue += (product.getPrice() * listOfCartItems.get(i).getQuantity());
            }else {
                totalValue += (product.getPrice() * listOfCartItems.get(i).getQuantity());
            }
        }
        return totalValue;
    }
}
