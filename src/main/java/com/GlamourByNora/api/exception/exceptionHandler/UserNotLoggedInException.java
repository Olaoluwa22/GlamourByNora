package com.GlamourByNora.api.exception.exceptionHandler;

public class UserNotLoggedInException extends RuntimeException{
    public UserNotLoggedInException(String message){
        super(message);
    }
}
