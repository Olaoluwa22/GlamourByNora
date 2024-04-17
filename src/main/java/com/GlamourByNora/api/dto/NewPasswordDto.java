package com.GlamourByNora.api.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class NewPasswordDto {
    @Size(min = 8)
    @Pattern(regexp="[a-zA-Z0-9]{8,}")
    private String newPassword;
    private String confirmNewPassword;
    public String getNewPassword() {
        return newPassword;
    }
    public void setNewPassword(String Password) {
        this.newPassword = Password;
    }
    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }
    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }

    @Override
    public String toString() {
        return "NewPasswordDto{" +
                "newPassword='" + newPassword + '\'' +
                ", confirmNewPassword='" + confirmNewPassword + '\'' +
                '}';
    }
}
