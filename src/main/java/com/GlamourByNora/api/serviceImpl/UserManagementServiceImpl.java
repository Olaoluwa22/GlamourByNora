package com.GlamourByNora.api.serviceImpl;

import com.GlamourByNora.api.dto.UserDto;
import com.GlamourByNora.api.dto.UserResponseDto;
import com.GlamourByNora.api.model.User;
import com.GlamourByNora.api.repository.UserRepository;
import com.GlamourByNora.api.response.ApiResponseMessages;
import com.GlamourByNora.api.service.UserManagementService;
import com.GlamourByNora.api.util.ConstantMessages;
import com.GlamourByNora.api.util.InfoGetter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserManagementServiceImpl implements UserManagementService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserResponseDto userResponseDto;
    @Autowired
    private InfoGetter infoGetter;
    @Override
    public ResponseEntity<?> getUsers() {
        ApiResponseMessages<List<UserResponseDto>> apiResponseMessages = new ApiResponseMessages<>();
        apiResponseMessages.setMessage(ConstantMessages.FAILED.getMessage());
        try {
            List<User> allUser = userRepository.findUserByDeleted(false);
            List<UserResponseDto> responseData = new ArrayList<>();
            allUser.forEach(user -> {
                UserResponseDto userResponseDto = new UserResponseDto();
                userResponseDto.setFirstName(user.getFirstName());
                userResponseDto.setLastName(user.getLastName());
                userResponseDto.setCountry(user.getCountry());
                userResponseDto.setState(user.getState());
                userResponseDto.setEmail(user.getEmail());
                userResponseDto.setPhoneNumber(user.getPhoneNumber());
                responseData.add(userResponseDto);
            });
            apiResponseMessages.setMessage(ConstantMessages.SUCCESS.getMessage());
            apiResponseMessages.setData(responseData);
            return new ResponseEntity<>(apiResponseMessages, HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(apiResponseMessages, HttpStatus.UNAUTHORIZED);
    }
    @Override
    public ResponseEntity<?> getUserById(Long userId) {
        ApiResponseMessages<User> apiResponseMessages = new ApiResponseMessages<>();
        User user = infoGetter.getUserById(userId);
        apiResponseMessages.setMessage(ConstantMessages.FOUND.getMessage());
        apiResponseMessages.setData(user);
        return new ResponseEntity<>(apiResponseMessages, HttpStatus.OK);
    }
    @Override
    public ResponseEntity<?> getUserByPageable(int page, int size) {
        ApiResponseMessages< List<UserResponseDto>> apiResponseMessages = new ApiResponseMessages<>();
        apiResponseMessages.setMessage(ConstantMessages.FAILED.getMessage());
        Pageable pageable = PageRequest.of(page,size);
        try {
            Page<User> allUser = userRepository.findUserByDeletedFalse(pageable);
            List<UserResponseDto> responseData = new ArrayList<>();
            allUser.forEach(user -> {
                UserResponseDto userResponseDto = new UserResponseDto();
                userResponseDto.setFirstName(user.getFirstName());
                userResponseDto.setLastName(user.getLastName());
                userResponseDto.setCountry(user.getCountry());
                userResponseDto.setState(user.getState());
                userResponseDto.setEmail(user.getEmail());
                userResponseDto.setPhoneNumber(user.getPhoneNumber());
                responseData.add(userResponseDto);
            });
            apiResponseMessages.setMessage(ConstantMessages.SUCCESS.getMessage());
            apiResponseMessages.setData(responseData);
            return new ResponseEntity<>(apiResponseMessages, HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(apiResponseMessages, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @Override
    public ResponseEntity<?> updateUserInfo(Long userId, UserDto userDto) {
        ApiResponseMessages<String> apiResponseMessages = new ApiResponseMessages<>();
        User user = infoGetter.getUserById(userId);
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
    @Override
    public ResponseEntity<?> deleteUser(Long userId) {
        ApiResponseMessages<String> apiResponseMessages = new ApiResponseMessages<>();
        User user = infoGetter.getUserByIdAndDeleted(userId, false);
        user.setDeleted(true);
        userRepository.save(user);
        apiResponseMessages.setMessage(ConstantMessages.ACCOUNT_HAS_BEEN_DELETED.getMessage());
        return new ResponseEntity<>(apiResponseMessages, HttpStatus.OK);
    }
}