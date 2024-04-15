package com.GlamourByNora.api.dto;

import com.GlamourByNora.api.util.PaystackVerificationData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaystackVerificationResponseDto {
    private String status;
    private String message;
    private PaystackVerificationData paystackVerificationData;

    public PaystackVerificationResponseDto(){}
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    public PaystackVerificationData getPaystackVerificationData() {
        return paystackVerificationData;
    }
    public void setPaystackVerificationData(PaystackVerificationData paystackVerificationData) {
        this.paystackVerificationData = paystackVerificationData;
    }

    @Override
    public String toString() {
        return "PaystackVerificationResponseDto{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", paystackVerificationData=" + paystackVerificationData +
                '}';
    }
}