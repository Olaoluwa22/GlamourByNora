package com.GlamourByNora.api.exception.exceptionHandler;

public class OrderNotFoundException extends Throwable {
    public OrderNotFoundException(String message) {
        super(message);
    }
}
