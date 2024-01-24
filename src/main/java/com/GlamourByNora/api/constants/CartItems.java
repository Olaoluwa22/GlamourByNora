package com.GlamourByNora.api.constants;
public class CartItems {
    private long productId;
    private int quantity;
    public CartItems(long productId, int quantity){
        this.productId = productId;
        this.quantity = quantity;
    }
    public long getProductId() {
        return productId;
    }
    public void setProductId(long productId) {
        this.productId = productId;
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
                "productId=" + productId +
                ", quantity=" + quantity +
                '}';
    }
}
