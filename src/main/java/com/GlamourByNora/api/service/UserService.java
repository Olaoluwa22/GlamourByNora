package com.GlamourByNora.api.service;

import com.GlamourByNora.api.dto.UserDto;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> updatePersonalInfo(UserDto userDto);
    String getUsernameOfLoggedInUser();
}