package com.GlamourByNora.api.controller;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public class ForgetPasswordDto {
    @Email
    @NotNull
    private String email;

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    @Override
    public String toString() {
        return "ForgetPasswordDto{" +
                "email='" + email + '\'' +
                '}';
    }
}
