package com.GlamourByNora.api.dto;

import jakarta.validation.constraints.NotNull;

public class VerificationCodeDto {
    @NotNull
    private String code;
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    @Override
    public String toString() {
        return "VerificationCodeDto{" +
                "code='" + code + '\'' +
                '}';
    }
}
