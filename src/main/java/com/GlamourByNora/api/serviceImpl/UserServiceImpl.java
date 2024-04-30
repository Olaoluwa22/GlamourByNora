package com.GlamourByNora.api.serviceImpl;

import com.GlamourByNora.api.dto.UserDto;
import com.GlamourByNora.api.model.User;
import com.GlamourByNora.api.repository.UserRepository;
import com.GlamourByNora.api.response.ApiResponseMessages;
import com.GlamourByNora.api.service.UserService;
import com.GlamourByNora.api.util.ConstantMessages;
import com.GlamourByNora.api.util.InfoGetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private InfoGetter infoGetter;

    @Override
    public ResponseEntity<?> updatePersonalInfo(UserDto userDto) {
        ApiResponseMessages<String> apiResponseMessages = new ApiResponseMessages<>();
        User user = infoGetter.getUser(getUsernameOfLoggedInUser());
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setCountry(userDto.getCountry());
        user.setState(userDto.getState());
        user.setAddress(userDto.getAddress());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setPhoneNumber(userDto.getPhoneNumber());
        userRepository.save(user);
        apiResponseMessages.setMessage(ConstantMessages.UPDATED.getMessage());
        return new ResponseEntity<>(apiResponseMessages, HttpStatus.OK);
    }
    public String getUsernameOfLoggedInUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null & authentication.getPrincipal() instanceof UserDetails){
            return ((UserDetails) authentication.getPrincipal()).getUsername();
        }
        return "INTERNAL SERVER ERROR...";
    }
}
