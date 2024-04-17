package com.GlamourByNora.api.exception.exceptionHandler;

import com.GlamourByNora.api.exception.ExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ControllerAdvice
public class DefaultExceptionHandler {
    @ExceptionHandler(UserNotLoggedInException.class)
    public ResponseEntity<?> userNotLoggedInException(UserNotLoggedInException exception){
        ExceptionResponse<Map<String, String>> exceptionResponse = new ExceptionResponse<>();
        exceptionResponse.setTimestamp(Instant.now());
        exceptionResponse.setMessage("User Not Logged In...");
        exceptionResponse.setStatus(HttpStatus.FORBIDDEN.value());
        exceptionResponse.setData(null);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(NotAuthorizedException.class)
    public ResponseEntity<?> notAuthorizedException(NotAuthorizedException exception){
        ExceptionResponse<Map<String, String>> exceptionResponse = new ExceptionResponse<>();
        exceptionResponse.setTimestamp(Instant.now());
        exceptionResponse.setMessage("User not authorized");
        exceptionResponse.setStatus(HttpStatus.FORBIDDEN.value());
        exceptionResponse.setData(null);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.FORBIDDEN);
    }
    @ExceptionHandler(EmailAlreadyExistException.class)
    public ResponseEntity<?> emailAlreadyExistException(EmailAlreadyExistException exception){
        ExceptionResponse<Map<String, String>> exceptionResponse = new ExceptionResponse<>();
        exceptionResponse.setTimestamp(Instant.now());
        exceptionResponse.setMessage("Email Already Exist");
        exceptionResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        exceptionResponse.setData(null);
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidException(MethodArgumentNotValidException exception){
        ExceptionResponse<Map<String, String>> exceptionResponse = new ExceptionResponse<>();
        exceptionResponse.setTimestamp(Instant.now());
        exceptionResponse.setMessage("Argument not valid...");
        exceptionResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        List<FieldError> fieldErrorList = exception.getFieldErrors();
        List<Map<String,String>> listOfError = new ArrayList<>();
        for (FieldError fieldError: fieldErrorList) {
            Map<String,String> error = new HashMap<>();
            error.put("field", fieldError.getField());
            error.put("message", fieldError.getDefaultMessage());
            listOfError.add(error);
        }
        exceptionResponse.setData(listOfError);
        return ResponseEntity.badRequest().body(exceptionResponse);
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> userNotFoundException(UserNotFoundException userNotFound){
        ExceptionResponse<Map<String, String>> exceptionResponse = new ExceptionResponse<>();
        exceptionResponse.setTimestamp(Instant.now());
        exceptionResponse.setMessage("User Not Found...");
        exceptionResponse.setData(null);
        exceptionResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(OrderNotFoundException.class)
    public ResponseEntity<?> orderNotFoundException(OrderNotFoundException orderNotFound){
        ExceptionResponse<Map<String, String>> exceptionResponse = new ExceptionResponse<>();
        exceptionResponse.setTimestamp(Instant.now());
        exceptionResponse.setMessage("Order Not Found...");
        exceptionResponse.setData(null);
        exceptionResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<?> productNotFoundException(ProductNotFoundException productNotFound){
        ExceptionResponse<Map<String, String>> exceptionResponse = new ExceptionResponse<>();
        exceptionResponse.setTimestamp(Instant.now());
        exceptionResponse.setMessage("Product Not Found...");
        exceptionResponse.setData(null);
        exceptionResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
