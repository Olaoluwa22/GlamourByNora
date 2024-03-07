package com.GlamourByNora.api.exception.exceptionHandler;

public class NotAuthorizedException extends RuntimeException {

    public NotAuthorizedException(String message){
        super(message);
    }
}
