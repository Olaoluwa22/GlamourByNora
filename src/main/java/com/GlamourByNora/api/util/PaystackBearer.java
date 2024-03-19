package com.GlamourByNora.api.util;

public enum PaystackBearer {
    ACCOUNT("subAccount");
    private String message;
    PaystackBearer(String message){
        this.message =message;
    }
    public String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }
    @Override
    public String toString() {
        return "PaystackBearer{" +
                "message='" + message + '\'' +
                '}';
    }
}
