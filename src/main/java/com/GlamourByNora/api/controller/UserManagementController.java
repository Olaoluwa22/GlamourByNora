package com.GlamourByNora.api.controller;

import com.GlamourByNora.api.dto.UserDto;
import com.GlamourByNora.api.service.UserManagementService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user_management")
public class UserManagementController {
    @Autowired
    private UserManagementService userManagementService;

    @GetMapping("/getAllUsers")
    public ResponseEntity<?> getUsers() {
        return userManagementService.getUsers();
    }
    @GetMapping("/page-list")
    public ResponseEntity<?> getUserByPageable(@RequestParam int page, @RequestParam int size) {
        return userManagementService.getUserByPageable(page, size);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable(name ="id") Long userId) {
        return userManagementService.getUserById(userId);
    }
    @PutMapping("/updateUserInfo/{id}")
    public ResponseEntity<?> updateUserInfo(@Valid @PathVariable(name="id")Long id, @RequestBody UserDto userDto){
        return userManagementService.updateUserInfo(id, userDto);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(name ="id")Long id) {
        return userManagementService.deleteUser(id);
    }
}