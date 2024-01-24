package com.GlamourByNora.api.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    private User user;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable (name = "cart_products",
               joinColumns = @JoinColumn(name = "cart_id"),
               inverseJoinColumns = @JoinColumn(name = "product_id"))
    private List<Product> products;

    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public List<Product> getProducts() {
        return products;
    }
    public void setProducts(List<Product> products) {
        this.products = products;
    }
    @Override
    public String toString() {
        return "Cart{" +
                "user=" + user +
                ", products=" + products +
                '}';
    }
}
