package com.GlamourByNora.api.controller;

import com.GlamourByNora.api.dto.PasswordDto;
import com.GlamourByNora.api.service.PasswordService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PasswordController {
    @Autowired
    private PasswordService passwordService;

    @PostMapping("/update-password")
    public ResponseEntity<?> updatePassword(@Valid @RequestBody PasswordDto passwordDto, HttpServletRequest request){
        return passwordService.updatePassword(passwordDto, request);
    }
}