package com.GlamourByNora.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 70, nullable = false)
    private String name;
    @Column(length = 70, nullable = false)
    private String brand;
    @Column(length = 100, nullable = false)
    private String fragranceFamily;
    @Column(length = 15, nullable = false)
    private String size;
    @Column(length = 70, nullable = false)
    private String type;
    @Column(length = 10, nullable = false)
    private String gender;
    private String description;
    @Column(length = 20, nullable = false)
    private double price;
    @Column(length = 20, nullable = false)
    private int stockQuantity;
    @Column(length = 255, nullable = false)
    private String imageUrl;
    @NotNull
    @NotEmpty
    private List<String> ingredients;
    @Column(nullable = false)
    private String productionDate;
    @Column(nullable = false)
    private String expiryDate;
    @NotNull
    private boolean availability;
    @Column(length = 70, nullable = false)
    private String countryOfOrigin;
    @ManyToMany(mappedBy = "products")
    private List<Cart> shoppingCarts;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                ", fragranceFamily='" + fragranceFamily + '\'' +
                ", size='" + size + '\'' +
                ", type='" + type + '\'' +
                ", gender='" + gender + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", stockQuantity=" + stockQuantity +
                ", imageUrl='" + imageUrl + '\'' +
                ", ingredients=" + ingredients +
                ", productionDate='" + productionDate + '\'' +
                ", expiryDate='" + expiryDate + '\'' +
                ", availability=" + availability +
                ", countryOfOrigin='" + countryOfOrigin + '\'' +
                ", shoppingCarts=" + shoppingCarts +
                '}';
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getBrand() {
        return brand;
    }
    public void setBrand(String brand) {
        this.brand = brand;
    }
    public String getFragranceFamily() {
        return fragranceFamily;
    }
    public void setFragranceFamily(String fragranceFamily) {
        this.fragranceFamily = fragranceFamily;
    }
    public String getSize() {
        return size;
    }
    public void setSize(String size) {
        this.size = size;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
    public int getStockQuantity() {
        return stockQuantity;
    }
    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public List<String> getIngredients() {
        return ingredients;
    }
    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
    public String getProductionDate() {
        return productionDate;
    }
    public void setProductionDate(String productionDate) {
        this.productionDate = productionDate;
    }
    public String getExpiryDate() {
        return expiryDate;
    }
    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
    public boolean isAvailability() {
        return availability;
    }
    public void setAvailability(boolean availability) {
        this.availability = availability;
    }
    public String getCountryOfOrigin() {
        return countryOfOrigin;
    }
    public void setCountryOfOrigin(String countryOfOrigin) {
        this.countryOfOrigin = countryOfOrigin;
    }
    public List<Cart> getShoppingCarts() {
        return shoppingCarts;
    }
    public void setShoppingCarts(List<Cart> shoppingCarts) {
        this.shoppingCarts = shoppingCarts;
    }
}
