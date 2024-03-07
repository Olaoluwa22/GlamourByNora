package com.GlamourByNora.api.paymentModel;

import com.GlamourByNora.api.util.VerificationData;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PaystackVerificationResponse {
    private String status;
    private String message;
    private VerificationData verificationData;

    public PaystackVerificationResponse(){}
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
    public VerificationData getVerificationData() {
        return verificationData;
    }
    public void setVerificationData(VerificationData verificationData) {
        this.verificationData = verificationData;
    }

    @Override
    public String toString() {
        return "PaystackVerificationResponse{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", verificationData=" + verificationData +
                '}';
    }
}
