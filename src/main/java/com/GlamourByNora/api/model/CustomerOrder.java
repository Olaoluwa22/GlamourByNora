package com.GlamourByNora.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

@Entity
public class CustomerOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String reference;
    @NotNull
    private int quantityOfProductsOrdered;
    @NotNull
    private int value;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @NotNull
    private String status;
    private Instant createdAt;
    private String paidAt;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getReference() {
        return reference;
    }
    public void setReference(String reference) {
        this.reference = reference;
    }
    public int getQuantityOfProductsOrdered() {
        return quantityOfProductsOrdered;
    }
    public void setQuantityOfProductsOrdered(int quantityOfProductsOrdered) {
        this.quantityOfProductsOrdered = quantityOfProductsOrdered;
    }
    public int getValue() {
        return value;
    }
    public void setValue(int value) {
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
    public Instant getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
    public String getPaidAt() {
        return paidAt;
    }
    public void setPaidAt(String paidAt) {
        this.paidAt = paidAt;
    }

    @Override
    public String toString() {
        return "CustomerOrder{" +
                "id=" + id +
                ", reference='" + reference + '\'' +
                ", quantityOfProductsOrdered=" + quantityOfProductsOrdered +
                ", value=" + value +
                ", status='" + status + '\'' +
                ", createdAt=" + createdAt +
                ", paidAt='" + paidAt + '\'' +
                '}';
    }
}
