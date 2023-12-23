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
        List<Map<String,String>> list = new ArrayList<>();
        for (FieldError fieldError: fieldErrorList) {
            Map<String,String> err = new HashMap<>();
            err.put("field", fieldError.getField());
            err.put("message", fieldError.getDefaultMessage());
            list.add(err);
        }
        exceptionResponse.setData(list);
        return ResponseEntity.badRequest().body(exceptionResponse);
    }
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<?> UserNotFoundException(UserNotFoundException userNotFound){
        ExceptionResponse<Map<String, String>> exceptionResponse = new ExceptionResponse<>();
        exceptionResponse.setTimestamp(Instant.now());
        exceptionResponse.setMessage("User Not Found...");
        exceptionResponse.setData(null);
        exceptionResponse.setStatus(HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(exceptionResponse, HttpStatus.BAD_REQUEST);
    }
}
