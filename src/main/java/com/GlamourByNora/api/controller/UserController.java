package com.GlamourByNora.api.controller;

import com.GlamourByNora.api.dto.UserDto;
import com.GlamourByNora.api.model.User;
import com.GlamourByNora.api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @GetMapping("/users")
    public List<User> getUsers(){
        return userService.getUsers();
    }
    @GetMapping("/user/get")
    public Page<User> getUserByPageable(@RequestParam int page, @RequestParam int size){
       return userService.getUserByPageable(page, size);
    }
    @GetMapping("/user/{id}")
    public Optional<User> getUserById(@PathVariable(name ="id") Long userId){
        return userService.getUserById(userId);
    }
    @PostMapping("/user/create")
    public ResponseEntity<?> createUser(@Valid @RequestBody UserDto userDto){
        return userService.createUser(userDto);
    }
    @PutMapping("/user/updateInfo/{id}")
    public ResponseEntity<?> updateUserInfo( @Valid @PathVariable(name="id")Long id, @RequestBody UserDto userDto){
        return userService.updateUserInfo(id, userDto);
    }
    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(name ="id")Long id){
       return userService.deleteUser(id);
    }
}