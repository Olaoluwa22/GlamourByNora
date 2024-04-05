package com.GlamourByNora.api.exception.exceptionHandler;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String message) {
        super(message);
    }
}
