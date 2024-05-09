package com.GlamourByNora.api.serviceImpl;

import com.GlamourByNora.api.dto.PaystackTransactionRequestDto;
import com.GlamourByNora.api.dto.PaystackVerificationResponseDto;
import com.GlamourByNora.api.exception.exceptionHandler.NotAuthorizedException;
import com.GlamourByNora.api.exception.exceptionHandler.OrderNotFoundException;
import com.GlamourByNora.api.model.Cart;
import com.GlamourByNora.api.model.CustomerOrder;
import com.GlamourByNora.api.model.Product;
import com.GlamourByNora.api.model.User;
import com.GlamourByNora.api.repository.CartRepository;
import com.GlamourByNora.api.repository.OrderRepository;
import com.GlamourByNora.api.repository.ProductRepository;
import com.GlamourByNora.api.repository.UserRepository;
import com.GlamourByNora.api.response.ApiResponseMessages;
import com.GlamourByNora.api.service.PaymentService;
import com.GlamourByNora.api.service.UserService;
import com.GlamourByNora.api.util.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
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

@Service
@RestControllerAdvice
public class PaymentServiceImpl implements PaymentService {
    private static final int STATUS_CODE_OK = 200;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private InfoGetter infoGetter;
    @Autowired
    private CartRepository cartRepository;

    private int validateQuantityAndGetTotalValue(HttpServletRequest request){
        HttpSession session = request.getSession(false);
        List<CartItems> listOfCartItems = (List<CartItems>) session.getAttribute("cart");
        int totalValue = 0;
        for (int i = 0; i < listOfCartItems.size(); i++) {
            Product product = infoGetter.getProduct(listOfCartItems.get(i).getProductId());
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
    private void extractCartItemsInSessionAndSaveToCartInDatabase(HttpServletRequest request, User user) {
        Cart cart = new Cart();
        List<Product> products = new ArrayList<>();
        HttpSession session = request.getSession(false);
        List<CartItems> cartItems = (List<CartItems>) session.getAttribute("cart");
        for (int i = 0; i < cartItems.size(); i++) {
            CartItems currentCartItem = cartItems.get(i);
            Product product = infoGetter.getProduct(currentCartItem.getProductId());
            products.add(product);
        }
        cart.setProducts(products);
        cart.setUser(user);
        cartRepository.save(cart);
        session.invalidate();
    }
    private void createOrder(HttpServletRequest request, User user) throws NullPointerException{
        ApiResponseMessages<String> apiResponseMessages = new ApiResponseMessages<>();
        HttpSession session = request.getSession(false);
        List<CartItems> cartItems = (List<CartItems>) session.getAttribute("cart");
        if(cartItems.isEmpty()){
            throw new NullPointerException();
        }
        CustomerOrder customerOrder = new CustomerOrder();
        customerOrder.setReference(new OrderReference().generateOrderReference());
        customerOrder.setQuantityOfProductsOrdered(cartItems.size());
        customerOrder.setStatus(ConstantMessages.PROCESSING.getMessage());
        customerOrder.setValue(validateQuantityAndGetTotalValue(request));
        customerOrder.setUser(user);
        customerOrder.setCreatedAt(Instant.now());
        orderRepository.save(customerOrder);
    }
    private String initializeTransaction(PaystackTransactionRequestDto paystackTransactionRequestDto, User user) throws IOException{
        String paymentUrl = null;
        CustomerOrder customerOrder = infoGetter.getOrderByUserIdAndStatus(user.getId(), ConstantMessages.PROCESSING.getMessage());
        paystackTransactionRequestDto.setAmount(customerOrder.getValue()*100);
        paystackTransactionRequestDto.setEmail(user.getEmail());
        paystackTransactionRequestDto.setReference(customerOrder.getReference());
//        paystackTransactionRequestDto.setCallback_url("http://localhost:8081/verify-payment/");
        try {
            Gson gson = new Gson();
            StringEntity postingData = new StringEntity(gson.toJson(paystackTransactionRequestDto));
            CloseableHttpClient client = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost("https://api.paystack.co/transaction/initialize");
            post.setEntity(postingData);
            post.addHeader("Content-type", "application/json");
            post.addHeader("Authorization", "Bearer sk_test_b5756e19ed7f96c84b253095358de89a137f8252");
            StringBuilder dataReceived = new StringBuilder();
            CloseableHttpResponse response = client.execute(post);
            if (response.getStatusLine().getStatusCode() == STATUS_CODE_OK) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    dataReceived.append(line);
                }
            } else {
                throw new NotAuthorizedException("Error Occurred in transaction process...");
            }
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(dataReceived.toString());
            paymentUrl = rootNode.get("data").get("authorization_url").asText();
        } catch (IOException ex) {
            throw new IOException("Failure initializing paystack transaction");
        }
        return paymentUrl;
    }
    @Override
    public ResponseEntity<?> checkout(HttpServletRequest request) {
        ApiResponseMessages<String> apiResponseMessages = new ApiResponseMessages<>();
        apiResponseMessages.setMessage(ConstantMessages.TOTAL_VALUE.getMessage());
        apiResponseMessages.setData(String.valueOf(validateQuantityAndGetTotalValue(request)));
        return new ResponseEntity<>(apiResponseMessages, HttpStatus.OK);
    }
    @Override
    @Transactional(rollbackOn = {NullPointerException.class, OrderNotFoundException.class})
    public ResponseEntity<?> proceedToPayment(HttpServletRequest request, PaystackTransactionRequestDto paystackTransactionRequestDto) throws IOException {
        ApiResponseMessages<String> apiResponseMessages = new ApiResponseMessages<>();
        apiResponseMessages.setMessage(ConstantMessages.FAILED.getMessage());
        User user = infoGetter.getUser(userService.getUsernameOfLoggedInUser());
        String paymentUrl;
        try {
            extractCartItemsInSessionAndSaveToCartInDatabase(request, user);
            createOrder(request, user);
            paymentUrl = initializeTransaction(paystackTransactionRequestDto, user);
        } catch (IOException e) {
            throw new IOException("Internal Error");
        }
        return new ResponseEntity<>(paymentUrl, HttpStatus.OK);
    }
    @Override
    public ResponseEntity<?> verifyPaystackTransaction(HttpServletRequest httpRequest) throws InterruptedException {
        ApiResponseMessages<String> apiResponseMessages = new ApiResponseMessages<>();
        apiResponseMessages.setMessage(ConstantMessages.FAILED.getMessage());
        User user = infoGetter.getUser(userService.getUsernameOfLoggedInUser());
        CustomerOrder customerOrder = infoGetter.getOrder(user.getId());
        PaystackVerificationResponseDto paystackVerificationResponseDto = null;
        try {
            CloseableHttpClient client = HttpClientBuilder.create().build();
            HttpGet request = new HttpGet("https://api.paystack.co/transaction/verify/"+ customerOrder.getReference());
            request.addHeader("Content-type", "application/json");
            request.addHeader("Authorization", "Bearer sk_test_b5756e19ed7f96c84b253095358de89a137f8252");
            StringBuilder dataReceived = new StringBuilder();
            CloseableHttpResponse response = client.execute(request);
            if (response.getStatusLine().getStatusCode() == STATUS_CODE_OK) {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    dataReceived.append(line);
                }
            }
            else {
                throw new InterruptedException("Error occurred while connecting to paystack verification url");
            }
            ObjectMapper mapper = new ObjectMapper();
            paystackVerificationResponseDto = mapper.readValue(dataReceived.toString(), PaystackVerificationResponseDto.class);
            if (paystackVerificationResponseDto == null || paystackVerificationResponseDto.getStatus().equals("false")) {
                throw new InterruptedException("An error occurred while verifying payment");
            } else if (paystackVerificationResponseDto.getData().getStatus().equals("success")) {
                Update update = new Update();
                update.updateOrder(customerOrder, paystackVerificationResponseDto.getData().getPaid_at());
                update.updateInventory(httpRequest);
                apiResponseMessages.setMessage(ConstantMessages.TRANSACTION_SUCCESSFUL.getMessage());
                return new ResponseEntity<>(apiResponseMessages, HttpStatus.OK);
            }
        } catch (InterruptedException | IOException | NumberFormatException | OrderNotFoundException ex) {
            throw new InterruptedException("Internal server error");
        }
        return new ResponseEntity<>(apiResponseMessages, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}