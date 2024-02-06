package com.GlamourByNora.api.repository;

import com.GlamourByNora.api.model.OTP;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OTPRepository extends JpaRepository<OTP, Long> {
    Optional<OTP> findByOtp(String otp);
}
