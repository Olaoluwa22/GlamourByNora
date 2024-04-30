package com.GlamourByNora.api.service;

import com.GlamourByNora.api.dto.UserDto;
import org.springframework.http.ResponseEntity;

public interface UserManagementService {
     ResponseEntity<?> getUsers();
     ResponseEntity<?> getUserById(Long userId);
     ResponseEntity<?> getUserByPageable(int page, int size);
     ResponseEntity<?> updateUserInfo(Long userId, UserDto userDto);
     ResponseEntity<?> deleteUser(Long userId);
}
