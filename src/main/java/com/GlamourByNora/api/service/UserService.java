package com.GlamourByNora.api.service;

import com.GlamourByNora.api.dto.UserDto;
import com.GlamourByNora.api.model.User;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface UserService {
    public ResponseEntity<?> createUser(UserDto userDto);
    public List<User> getUsers();
    public Optional<User> getUserById(Long userId);
    public Page<User> getUserByPageable(int page, int size);
    public ResponseEntity<?> updateUserInfo(Long userId, UserDto userDto);
    public ResponseEntity<?> deleteUser(Long userId);
}