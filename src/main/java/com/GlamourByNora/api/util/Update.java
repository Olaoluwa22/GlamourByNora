package com.GlamourByNora.api.util;

import com.GlamourByNora.api.exception.exceptionHandler.OrderNotFoundException;
import com.GlamourByNora.api.model.Order;
import com.GlamourByNora.api.model.Product;
import com.GlamourByNora.api.repository.OrderRepository;
import com.GlamourByNora.api.repository.ProductRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Optional;

public class Update {
    private OrderRepository orderRepository;
    private ProductRepository productRepository;
    public Update(){

    }
    public Update(OrderRepository orderRepository, ProductRepository productRepository) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
    }

    public void updateOrder(Order order, String transactionDate) throws OrderNotFoundException {
        order.setStatus("Paid");
        order.setPaidAt(transactionDate);
        orderRepository.save(order);
    }
    public void updateInventory(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        List<CartItems> cartItems = (List<CartItems>) session.getAttribute("cart");
        for (int i = 0; i < cartItems.size(); i++) {
            Optional<Product> optionalDatabaseProduct = productRepository.findProductById(cartItems.get(i).getProductId());
            Product product = optionalDatabaseProduct.get();
            int newStockQuantity = (product.getStockQuantity() - cartItems.get(i).getQuantity());
            product.setStockQuantity(newStockQuantity);
            productRepository.save(product);
            session.invalidate();
        }
    }
}
