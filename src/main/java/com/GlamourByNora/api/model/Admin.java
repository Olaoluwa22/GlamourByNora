package com.GlamourByNora.api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;

@Entity
public class Admin{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 50, nullable = false)
    private String firstname;
    @Column(length = 50, nullable = false)
    private String lastname;
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
    private Long phone_no;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
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

    public Long getPhone_no() {
        return phone_no;
    }

    public void setPhone_no(Long phone_no) {
        this.phone_no = phone_no;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "id=" + id +
                ", firstname='" + firstname + '\'' +
                ", lastname='" + lastname + '\'' +
                ", country='" + country + '\'' +
                ", state='" + state + '\'' +
                ", address='" + address + '\'' +
                ", email='" + email + '\'' +
                ", phone_no=" + phone_no +
                '}';
    }
}
