package com.GlamourByNora.api.serviceImpl;

import com.GlamourByNora.api.util.ConstantMessages;
import com.GlamourByNora.api.dto.UserDto;
import com.GlamourByNora.api.dto.UserResponseDto;
import com.GlamourByNora.api.exception.exceptionHandler.UserNotLoggedInException;
import com.GlamourByNora.api.model.User;
import com.GlamourByNora.api.repository.UserRepository;
import com.GlamourByNora.api.response.ApiResponseMessages;
import com.GlamourByNora.api.service.AppSecurityService;
import com.GlamourByNora.api.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserResponseDto userResponseDto;
    @Autowired
    private AppSecurityService appSecurityService;
    @Override
    public ResponseEntity<?> createUser(UserDto userDto) {
        ApiResponseMessages<String> apiResponseMessages = new ApiResponseMessages<>();
        apiResponseMessages.setMessage(ConstantMessages.FAILED.getMessage());
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setCountry(userDto.getCountry());
        user.setState(userDto.getState());
        user.setAddress(userDto.getAddress());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setPhoneNumber(userDto.getPhoneNumber());
        try {
            Optional<User> userInDatabase = userRepository.findUserByEmail(user.getEmail());
            if (userInDatabase.isPresent()) {
                apiResponseMessages.setMessage(ConstantMessages.EXIST.getMessage());
                return new ResponseEntity<>(apiResponseMessages, HttpStatus.BAD_REQUEST);
            }
            userRepository.save(user);
            apiResponseMessages.setMessage(ConstantMessages.CREATED.getMessage());
            return new ResponseEntity<>(apiResponseMessages, HttpStatus.CREATED);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(apiResponseMessages, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @Override
    public ResponseEntity<?> getUsers(HttpServletRequest request) throws UserNotLoggedInException {
        appSecurityService.getLoggedInUser(request);
        ApiResponseMessages< List<UserResponseDto>> apiResponseMessages = new ApiResponseMessages<>();
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
    public ResponseEntity<?> getUserById(Long userId, HttpServletRequest request) throws UserNotLoggedInException {
        appSecurityService.getLoggedInUser(request);
        ApiResponseMessages<String> apiResponseMessages = new ApiResponseMessages<>();
        apiResponseMessages.setMessage(ConstantMessages.FAILED.getMessage());
        try {
            Optional<User> byId = userRepository.findById(userId);
            if (byId.isEmpty()) {
                apiResponseMessages.setMessage(ConstantMessages.NOT_FOUND.getMessage());
                return new ResponseEntity<>(apiResponseMessages, HttpStatus.BAD_REQUEST);
            }
            User databaseUser = byId.get();
            if (databaseUser.isDeleted()) {
                apiResponseMessages.setMessage(ConstantMessages.NOT_FOUND.getMessage());
                return new ResponseEntity<>(apiResponseMessages, HttpStatus.BAD_REQUEST);
            }
            userResponseDto.setFirstName(databaseUser.getFirstName());
            userResponseDto.setLastName(databaseUser.getLastName());
            userResponseDto.setCountry(databaseUser.getCountry());
            userResponseDto.setState(databaseUser.getState());
            userResponseDto.setEmail(databaseUser.getEmail());
            userResponseDto.setPhoneNumber(databaseUser.getPhoneNumber());
            apiResponseMessages.setMessage(ConstantMessages.SUCCESS.getMessage());
            return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return new ResponseEntity<>(apiResponseMessages, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @Override
    public ResponseEntity<?> getUserByPageable(int page, int size, HttpServletRequest request) throws UserNotLoggedInException {
        appSecurityService.getLoggedInUser(request);
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
    public ResponseEntity<?> updateUserInfo(Long userId, UserDto userDto, HttpServletRequest request) throws UserNotLoggedInException {
        appSecurityService.getLoggedInUser(request);
        ApiResponseMessages<String> apiResponseMessages = new ApiResponseMessages<>();
        apiResponseMessages.setMessage(ConstantMessages.FAILED.getMessage());
        Optional<User> userInDatabase = userRepository.findById(userId);
        try{
            if (userInDatabase.isEmpty()) {
                apiResponseMessages.setMessage(ConstantMessages.NOT_FOUND.getMessage());
                return new ResponseEntity<>(apiResponseMessages, HttpStatus.BAD_REQUEST);
            }
            User databaseUser =userInDatabase.get();
            databaseUser.setFirstName(userDto.getFirstName());
            databaseUser.setLastName(userDto.getLastName());
            databaseUser.setCountry(userDto.getCountry());
            databaseUser.setState(userDto.getState());
            databaseUser.setAddress(userDto.getAddress());
            databaseUser.setEmail(userDto.getEmail());
            databaseUser.setPassword(userDto.getPassword());
            databaseUser.setPhoneNumber(userDto.getPhoneNumber());
            userRepository.save(databaseUser);
            apiResponseMessages.setMessage(ConstantMessages.UPDATED.getMessage());
            return new ResponseEntity<>(apiResponseMessages, HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(apiResponseMessages, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @Override
    public ResponseEntity<?> deleteUser(Long userId, HttpServletRequest request) throws UserNotLoggedInException {
        appSecurityService.getLoggedInUser(request);
        ApiResponseMessages<String> apiResponseMessages = new ApiResponseMessages<>();
        apiResponseMessages.setMessage(ConstantMessages.DELETED.getMessage());
        try {
            Optional<User> byId = userRepository.findById(userId);
            if (byId.isEmpty()) {
                apiResponseMessages.setMessage(ConstantMessages.NOT_FOUND.getMessage());
                return new ResponseEntity<>(apiResponseMessages, HttpStatus.BAD_REQUEST);
            }
            User databaseUser = byId.get();
            databaseUser.setDeleted(true);
            userRepository.save(databaseUser);
            apiResponseMessages.setMessage(ConstantMessages.DELETED.getMessage());
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(apiResponseMessages, HttpStatus.OK);
    }
}
