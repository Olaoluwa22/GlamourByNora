package com.GlamourByNora.api.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public class ProductDto {

    @NotNull
    private String name;
    @NotNull
    private String brand;
    @NotNull
    private String fragranceFamily;
    @NotNull
    private String size;
    @NotNull
    private String type;
    @NotNull
    private String gender;
    @NotNull
    private String description;
    @NotNull
    private int price;
    @NotNull
    private int stockQuantity;
    @NotNull
    private String imageUrl;
    private List<String> ingredients;
    @NotNull
    private String productionDate;
    @NotNull
    private String expiryDate;
    @NotNull
    private boolean availability;
    @NotNull
    @Size(min=4, max=75)
    private String countryOfOrigin;
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
    public int getPrice() {
        return price;
    }
    public void setPrice(int price) {
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
    @Override
    public String toString() {
        return "Product{" +
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
                ", productionDate=" + productionDate +
                ", expiryDate=" + expiryDate +
                ", availability=" + availability +
                ", countryOfOrigin='" + countryOfOrigin + '\'' +
                '}';
    }
}
