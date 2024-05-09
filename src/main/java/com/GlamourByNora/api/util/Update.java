package com.GlamourByNora.api.util;

import com.GlamourByNora.api.exception.exceptionHandler.OrderNotFoundException;
import com.GlamourByNora.api.model.CustomerOrder;
import com.GlamourByNora.api.model.Product;
import com.GlamourByNora.api.repository.OrderRepository;
import com.GlamourByNora.api.repository.ProductRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class Update {
    private OrderRepository orderRepository;
    private ProductRepository productRepository;
    private InfoGetter infoGetter;
    public Update(){

    }
    public Update(OrderRepository orderRepository, ProductRepository productRepository, InfoGetter infoGetter) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.infoGetter = infoGetter;
    }

    public void updateOrder(CustomerOrder customerOrder, String paidAt) throws OrderNotFoundException {
        customerOrder.setStatus("Paid");
        customerOrder.setPaidAt(paidAt);
        orderRepository.save(customerOrder);
    }
    public void updateInventory(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        List<CartItems> cartItems = (List<CartItems>) session.getAttribute("cart");
        for (int i = 0; i < cartItems.size(); i++) {
            Product product = infoGetter.getProduct(cartItems.get(i).getProductId());
            int newStockQuantity = (product.getStockQuantity() - cartItems.get(i).getQuantity());
            product.setStockQuantity(newStockQuantity);
            productRepository.save(product);
        }
        session.invalidate();
    }
}
