package com.GlamourByNora.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

import java.util.Arrays;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50, nullable = false)
    private String firstName;
    @Column(length = 50, nullable = false)
    private String lastName;
    @Column(length = 50, nullable = false)
    private String country;
    @Column(length = 50, nullable = false)
    private String state;
    @Column(length = 50, nullable = false)
    private String address;
    @Column(length = 100, nullable = false)
    @Email
    private String email;
    @Column(length = 13, nullable = false)
    private Long phoneNumber;
    private String password;
    private String role;
    private boolean deleted;
    @OneToOne(mappedBy = "user")
    @JoinColumn(name = "user_id")
    private Cart shoppingCart;
    @OneToMany(mappedBy = "user")
    private List<CustomerOrder> customerOrders;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Long getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(Long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public boolean isDeleted() {
        return deleted;
    }
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public List<String> getRoleAsList(){
        return Arrays.asList(this.role);
    }
    public Cart getShoppingCart() {
        return shoppingCart;
    }
    public Cart setShoppingCart(Cart shoppingCart) {
        this.shoppingCart = shoppingCart;
        return shoppingCart;
    }
    public List<CustomerOrder> getOrders() {
        return customerOrders;
    }
    public void setOrders(List<CustomerOrder> customerOrders) {
        this.customerOrders = customerOrders;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", country='" + country + '\'' +
                ", state='" + state + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", password='" + password + '\'' +
                ", deleted=" + deleted +
                ", customerOrders=" + customerOrders +
                '}';
    }
}
