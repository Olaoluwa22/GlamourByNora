package com.GlamourByNora.api.controller;

import com.GlamourByNora.api.dto.UserDto;
import com.GlamourByNora.api.model.User;
import com.GlamourByNora.api.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @GetMapping("/users")
    public List<User> getUsers(){
        return userRepository.findAll();
    }
    @GetMapping("/user/get")
    public Page<User> getUserByPageable(@RequestParam int page, @RequestParam int size){
        Pageable pageable = PageRequest.of(page,size);
        return userRepository.findAll(pageable);
    }
    @GetMapping("/user/{id}")
    public Optional<User> getUserbyId(@PathVariable(name ="id") Long userId){
        return userRepository.findById(userId);
    }
    @PostMapping("/user/create")
    public String createUser(@Valid @RequestBody UserDto userDto){
        User user = new User();
        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        user.setCountry(userDto.getCountry());
        user.setState(userDto.getState());
        user.setAddress(userDto.getAddress());
        user.setEmail(userDto.getEmail());
        user.setPhone_no(userDto.getPhone_no());
        userRepository.save(user);
        return "New User created...";
    }
    @PutMapping("/updateInfo/{id}")
    public String updateUserInfo( @Valid @PathVariable(name="id")Long id, @RequestBody UserDto userDto){
        User user = userRepository.findById(id).get();
        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        user.setCountry(userDto.getCountry());
        user.setState(userDto.getState());
        user.setAddress(userDto.getAddress());
        user.setEmail(userDto.getEmail());
        user.setPhone_no(userDto.getPhone_no());
        userRepository.save(user);
        return "User info updated...";
    }
    @DeleteMapping("/delete/{id}")
    public String deleteUser(@PathVariable(name ="id")Long id){
        userRepository.deleteById(id);
        return "User"+id+"'s info has been deleted Successfully...";
    }

}