package com.GlamourByNora.api.controller;

import com.GlamourByNora.api.dto.UserDto;
import com.GlamourByNora.api.dto.UserResponseDto;
import com.GlamourByNora.api.model.User;
import com.GlamourByNora.api.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    private UserService userService;
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<?> getUsers(HttpServletRequest request) throws Exception {
        return userService.getUsers(request);
    }
    @GetMapping("/user/page-list")
    public ResponseEntity<?> getUserByPageable(@RequestParam int page, @RequestParam int size, HttpServletRequest request) throws Exception {
       return userService.getUserByPageable(page, size, request);
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable(name ="id") Long userId, HttpServletRequest request) throws Exception {
        return userService.getUserById(userId, request);
    }
    @PostMapping("/user/create")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDto userDto){
        return userService.createUser(userDto);
    }
    @PutMapping("/user/updateInfo/{id}")
    public ResponseEntity<?> updateUserInfo( @Valid @PathVariable(name="id")Long id, @RequestBody UserDto userDto, HttpServletRequest request) throws Exception {
        return userService.updateUserInfo(id, userDto, request);
    }
    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(name ="id")Long id, HttpServletRequest request) throws Exception {
       return userService.deleteUser(id, request);
    }
}