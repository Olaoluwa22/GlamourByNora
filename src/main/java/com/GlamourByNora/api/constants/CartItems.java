package com.GlamourByNora.api.constants;

import com.GlamourByNora.api.model.Product;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public class CartItems {
    private Product product;
    private int quantity;
    public CartItems(Product product, int quantity){
        this.product = product;
        this.quantity = quantity;
    }

    public Product getProduct() {
        return product;
    }
    public void setProducts(Product product) {
        this.product = product;
    }
    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    @Override
    public String toString() {
        return "CartItems{" +
                "product=" + product +
                ", quantity=" + quantity +
                '}';
    }
}
