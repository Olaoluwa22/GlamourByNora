package com.GlamourByNora.api.serviceImpl;

import com.GlamourByNora.api.constants.ConstantMessages;
import com.GlamourByNora.api.dto.UserDto;
import com.GlamourByNora.api.dto.UserResponseDto;
import com.GlamourByNora.api.exception.exceptionHandler.UserNotLoggedInException;
import com.GlamourByNora.api.model.User;
import com.GlamourByNora.api.repository.UserRepository;
import com.GlamourByNora.api.response.ApiResponseMessages;
import com.GlamourByNora.api.service.AuthenticationService;
import com.GlamourByNora.api.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.hibernate.internal.build.AllowPrintStacktrace;
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
    private AuthenticationService authenticationService;
    @Override
    public ResponseEntity<?> createUser(UserDto userDto) {
        ApiResponseMessages<String> apiResponseMessages = new ApiResponseMessages<>();
        apiResponseMessages.setMessage(ConstantMessages.FAILED.getMessage());
        User user = new User();
        user.setFirstname(userDto.getFirstname());
        user.setLastname(userDto.getLastname());
        user.setCountry(userDto.getCountry());
        user.setState(userDto.getState());
        user.setAddress(userDto.getAddress());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setPhone_no(userDto.getPhone_no());
        try {
            Optional<User> byEmail = userRepository.findByEmail(user.getEmail());
            if (byEmail.isPresent()) {
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
        authenticationService.isUserLoggedIn(request);
        ApiResponseMessages< List<UserResponseDto>> apiResponseMessages = new ApiResponseMessages<>();
        apiResponseMessages.setMessage(ConstantMessages.FAILED.getMessage());
        try {
            List<User> allUser = userRepository.findByDeleted(false);
            List<UserResponseDto> responseData = new ArrayList<>();
            allUser.forEach(user -> {
                UserResponseDto userResponseDto = new UserResponseDto();
                userResponseDto.setFirstname(user.getFirstname());
                userResponseDto.setLastname(user.getLastname());
                userResponseDto.setCountry(user.getCountry());
                userResponseDto.setState(user.getState());
                userResponseDto.setEmail(user.getEmail());
                userResponseDto.setPhone_no(user.getPhone_no());
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
        authenticationService.isUserLoggedIn(request);
        ApiResponseMessages<String> apiResponseMessages = new ApiResponseMessages<>();
        apiResponseMessages.setMessage(ConstantMessages.FAILED.getMessage());
        try {
            Optional<User> byId = userRepository.findById(userId);
            if (byId.isEmpty()) {
                apiResponseMessages.setMessage(ConstantMessages.NOT_FOUND.getMessage());
                return new ResponseEntity<>(apiResponseMessages, HttpStatus.BAD_REQUEST);
            }
            User user = byId.get();
            if (user.isDeleted()) {
                apiResponseMessages.setMessage(ConstantMessages.NOT_FOUND.getMessage());
                return new ResponseEntity<>(apiResponseMessages, HttpStatus.BAD_REQUEST);
            }
            userResponseDto.setFirstname(user.getFirstname());
            userResponseDto.setLastname(user.getLastname());
            userResponseDto.setCountry(user.getCountry());
            userResponseDto.setState(user.getState());
            userResponseDto.setEmail(user.getEmail());
            userResponseDto.setPhone_no(user.getPhone_no());
            apiResponseMessages.setMessage(ConstantMessages.SUCCESS.getMessage());
            return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return new ResponseEntity<>(apiResponseMessages, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @Override
    public ResponseEntity<?> getUserByPageable(int page, int size, HttpServletRequest request) throws UserNotLoggedInException {
        authenticationService.isUserLoggedIn(request);
        ApiResponseMessages< List<UserResponseDto>> apiResponseMessages = new ApiResponseMessages<>();
        apiResponseMessages.setMessage(ConstantMessages.FAILED.getMessage());
        Pageable pageable = PageRequest.of(page,size);
        try {
            Page<User> allUser = userRepository.findByDeletedFalse(pageable);
            List<UserResponseDto> responseData = new ArrayList<>();
            allUser.forEach(user -> {
                UserResponseDto userResponseDto = new UserResponseDto();
                userResponseDto.setFirstname(user.getFirstname());
                userResponseDto.setLastname(user.getLastname());
                userResponseDto.setCountry(user.getCountry());
                userResponseDto.setState(user.getState());
                userResponseDto.setEmail(user.getEmail());
                userResponseDto.setPhone_no(user.getPhone_no());
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
        authenticationService.isUserLoggedIn(request);
        ApiResponseMessages<String> apiResponseMessages = new ApiResponseMessages<>();
        apiResponseMessages.setMessage(ConstantMessages.FAILED.getMessage());
        Optional<User> byId = userRepository.findById(userId);
        try{
            if (byId.isEmpty()) {
                apiResponseMessages.setMessage(ConstantMessages.NOT_FOUND.getMessage());
                return new ResponseEntity<>(apiResponseMessages, HttpStatus.BAD_REQUEST);
            }
            User user =byId.get();
            user.setFirstname(userDto.getFirstname());
            user.setLastname(userDto.getLastname());
            user.setCountry(userDto.getCountry());
            user.setState(userDto.getState());
            user.setAddress(userDto.getAddress());
            user.setEmail(userDto.getEmail());
            user.setPassword(userDto.getPassword());
            user.setPhone_no(userDto.getPhone_no());
            userRepository.save(user);
            apiResponseMessages.setMessage(ConstantMessages.UPDATED.getMessage());
            return new ResponseEntity<>(apiResponseMessages, HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(apiResponseMessages, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @Override
    public ResponseEntity<?> deleteUser(Long userId, HttpServletRequest request) throws UserNotLoggedInException {
        authenticationService.isUserLoggedIn(request);
        ApiResponseMessages<String> apiResponseMessages = new ApiResponseMessages<>();
        apiResponseMessages.setMessage(ConstantMessages.DELETED.getMessage());
        try {
            Optional<User> byId = userRepository.findById(userId);
            if (byId.isEmpty()) {
                apiResponseMessages.setMessage(ConstantMessages.NOT_FOUND.getMessage());
                return new ResponseEntity<>(apiResponseMessages, HttpStatus.BAD_REQUEST);
            }
            User user = byId.get();
            user.setDeleted(true);
            userRepository.save(user);
            apiResponseMessages.setMessage(ConstantMessages.DELETED.getMessage());
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(apiResponseMessages, HttpStatus.OK);
    }
}