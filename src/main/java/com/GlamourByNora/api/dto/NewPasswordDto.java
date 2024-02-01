package com.GlamourByNora.api.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class NewPasswordDto {
    @Size(min = 8)
    @Pattern(regexp="[a-zA-Z0-9]{8,}")
    private String Password;
    private String confirmPassword;
    public String getPassword() {
        return Password;
    }
    public void setPassword(String Password) {
        this.Password = Password;
    }
    public String getConfirmPassword() {
        return confirmPassword;
    }
    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @Override
    public String toString() {
        return "NewPasswordDto{" +
                "Password='" + Password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                '}';
    }
}
