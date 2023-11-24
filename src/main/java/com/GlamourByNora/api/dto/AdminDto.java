package com.GlamourByNora.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class AdminDto {
    private Long id;
    @NotBlank
    @NotNull
    @NotEmpty
    private String firstname;
    @NotBlank
    @NotNull
    @NotEmpty
    private String lastname;
    @NotBlank
    @NotNull
    @NotEmpty
    private String country;
    @NotBlank
    @NotNull
    @NotEmpty
    private String state;
    @NotBlank
    @NotNull
    @NotEmpty
    private String address;
    @NotBlank
    @NotNull
    @NotEmpty
    private String email;
    @NotNull
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
        return "User{" +
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
