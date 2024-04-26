package com.GlamourByNora.api.controller;

import com.GlamourByNora.api.dto.UserDto;
import com.GlamourByNora.api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserService userService;
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/getAllUsers")
    public ResponseEntity<?> getUsers() {
        return userService.getUsers();
    }
    @GetMapping("/page-list")
    public ResponseEntity<?> getUserByPageable(@RequestParam int page, @RequestParam int size) {
       return userService.getUserByPageable(page, size);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable(name ="id") Long userId) {
        return userService.getUserById(userId);
    }
    @PutMapping("/updateUserInfo/{id}")
    public ResponseEntity<?> updateUserInfo( @Valid @PathVariable(name="id")Long id, @RequestBody UserDto userDto){
        return userService.updateUserInfo(id, userDto);
    }
    @PutMapping("/update-personal-info")
    public ResponseEntity<?> updatePersonalInfo( @Valid @RequestBody UserDto userDto) {
        return userService.updatePersonalInfo(userDto);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(name ="id")Long id) {
       return userService.deleteUser(id);
    }
}