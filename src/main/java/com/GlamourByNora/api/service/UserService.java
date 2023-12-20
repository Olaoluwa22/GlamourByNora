package com.GlamourByNora.api.service;

import com.GlamourByNora.api.dto.UserDto;
import com.GlamourByNora.api.model.User;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface UserService {
    public ResponseEntity<?> createUser(UserDto userDto);
    public ResponseEntity<?> getUsers(HttpServletRequest request) throws Exception;
    public ResponseEntity<?> getUserById(Long userId, HttpServletRequest request) throws Exception;
    public ResponseEntity<?> getUserByPageable(int page, int size, HttpServletRequest request) throws Exception;
    public ResponseEntity<?> updateUserInfo(Long userId, UserDto userDto, HttpServletRequest request) throws Exception;
    public ResponseEntity<?> deleteUser(Long userId, HttpServletRequest request) throws Exception;
}