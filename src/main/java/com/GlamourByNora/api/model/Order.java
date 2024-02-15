package com.GlamourByNora.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
@Entity
@Table(name = "`order`")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String orderNumber;
    @NotNull
    private int quantityOfProduct;
    @NotNull
    private String value;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @NotNull
    private String status;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getOrderNumber() {
        return orderNumber;
    }
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }
    public int getQuantityOfProduct() {
        return quantityOfProduct;
    }
    public void setQuantityOfProduct(int quantityOfProduct) {
        this.quantityOfProduct = quantityOfProduct;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderNumber='" + orderNumber + '\'' +
                ", quantityOfProduct=" + quantityOfProduct +
                ", value='" + value + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
