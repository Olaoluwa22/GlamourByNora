package com.GlamourByNora.api.controller;

import com.GlamourByNora.api.dto.UserDto;
import com.GlamourByNora.api.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
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
    public ResponseEntity<?> getUsers(HttpServletRequest request) throws Exception {
        return userService.getUsers(request);
    }
    @GetMapping("/page-list")
    public ResponseEntity<?> getUserByPageable(@RequestParam int page, @RequestParam int size, HttpServletRequest request) throws Exception {
       return userService.getUserByPageable(page, size, request);
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable(name ="id") Long userId, HttpServletRequest request) throws Exception {
        return userService.getUserById(userId, request);
    }
    @PutMapping("/updateInfo/{id}")
    public ResponseEntity<?> updateUserInfo( @Valid @PathVariable(name="id")Long id, @RequestBody UserDto userDto, HttpServletRequest request) throws Exception {
        return userService.updateUserInfo(id, userDto, request);
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(name ="id")Long id, HttpServletRequest request) throws Exception {
       return userService.deleteUser(id, request);
    }
}