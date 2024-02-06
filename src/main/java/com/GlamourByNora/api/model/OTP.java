package com.GlamourByNora.api.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;

import java.time.Instant;

@Entity
public class OTP {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotNull
    private String otp;
    @NotNull
    private Instant createdAt;
    @NotNull
    private Instant expiresAt;
    @NotNull
    private long userId;
    @NotNull
    private boolean expired;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getOtp() {
        return otp;
    }
    public void setOtp(String otp) {
        this.otp = otp;
    }
    public Instant getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
    public Instant getExpiresAt() {
        return expiresAt;
    }
    public void setExpiresAt(Instant expiresAt) {
        this.expiresAt = expiresAt;
    }
    public boolean expired() {
        return expired;
    }
    public void setExpired(boolean expired) {
        this.expired = expired;
    }
    public long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "OTP{" +
                "id=" + id +
                ", otp='" + otp + '\'' +
                ", createdAt=" + createdAt +
                ", expiresAt=" + expiresAt +
                ", userId=" + userId +
                ", expired=" + expired +
                '}';
    }
}