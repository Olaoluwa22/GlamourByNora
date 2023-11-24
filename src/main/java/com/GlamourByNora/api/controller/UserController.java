package com.GlamourByNora.api.controller;

import com.GlamourByNora.api.dto.UserDto;
import com.GlamourByNora.api.model.User;
import com.GlamourByNora.api.repository.UserRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;
    @GetMapping("/users")
    public List<User> getUsers(){
        return userRepository.findAll();
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
