package com.GlamourByNora.api.controller;

import com.GlamourByNora.api.dto.UserDto;
import com.GlamourByNora.api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PutMapping("/update-personal-info")
    public ResponseEntity<?> updatePersonalInfo( @Valid @RequestBody UserDto userDto) {
        return userService.updatePersonalInfo(userDto);
    }
}