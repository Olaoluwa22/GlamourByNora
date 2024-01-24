package com.GlamourByNora.api.dto;

import jakarta.validation.constraints.NotNull;
public class CartRequestDto {
    @NotNull
    private long productId;
    public long getProductId() {
        return productId;
    }
    public void setProductId(long productId) {
        this.productId = productId;
    }

    @Override
    public String toString() {
        return "CartRequestDto{" +
                "productId=" + productId +
                '}';
    }
}
