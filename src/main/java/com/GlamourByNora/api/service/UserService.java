package com.GlamourByNora.api.service;

import com.GlamourByNora.api.dto.UserDto;
import org.springframework.http.ResponseEntity;

public interface UserService {
    public ResponseEntity<?> getUsers();
    public ResponseEntity<?> getUserById(Long userId);
    public ResponseEntity<?> getUserByPageable(int page, int size);
    public ResponseEntity<?> updateUserInfo(Long userId, UserDto userDto);
    ResponseEntity<?> updatePersonalInfo(UserDto userDto);
    public ResponseEntity<?> deleteUser(Long userId);
    String getUsernameOfLoggedInUser();
}