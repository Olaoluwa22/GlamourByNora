package com.GlamourByNora.api.serviceImpl;

import com.GlamourByNora.api.dto.AuthenticationDto;
import com.GlamourByNora.api.dto.SignupRequestDto;
import com.GlamourByNora.api.exception.exceptionHandler.EmailAlreadyExistException;
import com.GlamourByNora.api.jwt.service.JwtBlacklistService;
import com.GlamourByNora.api.jwt.service.JwtTokenService;
import com.GlamourByNora.api.model.User;
import com.GlamourByNora.api.repository.UserRepository;
import com.GlamourByNora.api.response.ApiResponseMessages;
import com.GlamourByNora.api.service.AuthenticationService;
import com.GlamourByNora.api.service.UserService;
import com.GlamourByNora.api.util.ConstantMessages;
import com.GlamourByNora.api.util.InfoGetter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private InfoGetter infoGetter;
    @Autowired
    private JwtTokenService jwtTokenService;
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtBlacklistService blacklistService;
    @Override
    public ResponseEntity<?> signup(SignupRequestDto signupRequestDto) {
        ApiResponseMessages<String> apiResponseMessages = new ApiResponseMessages<>();
        apiResponseMessages.setMessage(ConstantMessages.FAILED.getMessage());
        User user = new User();
        user.setFirstName(signupRequestDto.getFirstName());
        user.setLastName(signupRequestDto.getLastName());
        user.setCountry(signupRequestDto.getCountry());
        user.setState(signupRequestDto.getState());
        user.setAddress(signupRequestDto.getAddress());
        user.setEmail(signupRequestDto.getEmail());
        user.setPassword(passwordEncoder.encode(signupRequestDto.getPassword()));
        user.setPhoneNumber(signupRequestDto.getPhoneNumber());
        user.setDeleted(false);
        user.setRole("User");
        try {
            Optional<User> optionalUser = userRepository.findUserByEmail(user.getEmail());
            if (optionalUser.isPresent()) {
                apiResponseMessages.setMessage(ConstantMessages.EXIST.getMessage());
                return new ResponseEntity<>(apiResponseMessages, HttpStatus.BAD_REQUEST);
            }
            userRepository.save(user);
            String token = jwtTokenService.createToken(user.getEmail(), user.getRoleAsList());
            apiResponseMessages.setMessage(ConstantMessages.CREATED.getMessage());
            apiResponseMessages.setData(token);
            return new ResponseEntity<>(apiResponseMessages, HttpStatus.CREATED);
        }catch (EmailAlreadyExistException ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(apiResponseMessages, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @Override
    public ResponseEntity<?> login(AuthenticationDto authenticationDto, HttpServletResponse response, HttpServletRequest request) {
        ApiResponseMessages<String> apiResponseMessages = new ApiResponseMessages<>();
        apiResponseMessages.setMessage(ConstantMessages.FAILED.getMessage());
        try {
            User user = infoGetter.getUserByEmailAndDeleted(authenticationDto.getUsername(), false);
            if (!authenticationDto.getPassword().equals(user.getPassword())) {
                apiResponseMessages.setMessage(ConstantMessages.INCORRECT.getMessage());
                return new ResponseEntity<>(apiResponseMessages, HttpStatus.BAD_REQUEST);
            }
            String token = jwtTokenService.createToken(user.getEmail(), user.getRoleAsList());
            apiResponseMessages.setData(token);
            apiResponseMessages.setMessage(ConstantMessages.USER_LOGGED_IN_SUCCESSFULLY.getMessage());
            return new ResponseEntity<>(apiResponseMessages, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(apiResponseMessages, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @Override
    public ResponseEntity<?> logout(HttpServletRequest request) {
        ApiResponseMessages<String> apiResponseMessages = new ApiResponseMessages<>();
        blacklistService.invalidateToken(request);
        apiResponseMessages.setMessage(ConstantMessages.LOGGED_OUT.getMessage());
        return new ResponseEntity<>(apiResponseMessages, HttpStatus.OK);
        }
}