package com.GlamourByNora.api.service;

import com.GlamourByNora.api.dto.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface UserService {
    public ResponseEntity<?> getUsers(HttpServletRequest request) throws Exception;
    public ResponseEntity<?> getUserById(Long userId, HttpServletRequest request) throws Exception;
    public ResponseEntity<?> getUserByPageable(int page, int size, HttpServletRequest request) throws Exception;
    public ResponseEntity<?> updateUserInfo(Long userId, UserDto userDto, HttpServletRequest request) throws Exception;
    public ResponseEntity<?> deleteUser(Long userId, HttpServletRequest request) throws Exception;
    String getUsernameOfLoggedInUser();
}