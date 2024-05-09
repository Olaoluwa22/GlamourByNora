package com.GlamourByNora.api.util;

import com.GlamourByNora.api.model.Cart;
import com.GlamourByNora.api.model.Product;
import com.GlamourByNora.api.model.User;
import com.GlamourByNora.api.repository.CartRepository;
import com.GlamourByNora.api.repository.ProductRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.ArrayList;
import java.util.List;

public class SaveToCart {
    private InfoGetter infoGetter;
    private CartRepository cartRepository;
    private ProductRepository productRepository;

    public void saveToCartInDatabase(HttpServletRequest request, User user) {
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
}