package com.GlamourByNora.api.util;

import com.GlamourByNora.api.model.OTP;
import com.GlamourByNora.api.repository.OTPRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;

public class GenerateOTP {
    @Autowired
    private OTPRepository otpRepository;
    public String generateOTP(){


        return null;
    }
}