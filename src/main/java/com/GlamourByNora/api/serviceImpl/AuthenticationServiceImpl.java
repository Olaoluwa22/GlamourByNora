package com.GlamourByNora.api.serviceImpl;

import com.GlamourByNora.api.dto.AuthenticationDto;
import com.GlamourByNora.api.dto.SignupRequestDto;
import com.GlamourByNora.api.exception.exceptionHandler.EmailAlreadyExistException;
import com.GlamourByNora.api.jwt.JwtService;
import com.GlamourByNora.api.model.User;
import com.GlamourByNora.api.repository.UserRepository;
import com.GlamourByNora.api.response.ApiResponseMessages;
import com.GlamourByNora.api.service.AuthenticationService;
import com.GlamourByNora.api.util.ConstantMessages;
import com.GlamourByNora.api.util.ConstantMethod;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private UserRepository userRepository;
    private ConstantMethod constantMethod;
    private JwtService jwtService;
    public AuthenticationServiceImpl(UserRepository userRepository, ConstantMethod constantMethod, JwtService jwtService) {
        this.userRepository = userRepository;
        this.constantMethod = constantMethod;
        this.jwtService = jwtService;
    }
    @Override
    public ResponseEntity<?> signup(SignupRequestDto signupDto) {
        ApiResponseMessages<String> apiResponseMessages = new ApiResponseMessages<>();
        apiResponseMessages.setMessage(ConstantMessages.FAILED.getMessage());
        User user = new User();
        user.setFirstName(signupDto.getFirstName());
        user.setLastName(signupDto.getLastName());
        user.setCountry(signupDto.getCountry());
        user.setState(signupDto.getState());
        user.setAddress(signupDto.getAddress());
        user.setEmail(signupDto.getEmail());
        user.setPassword(signupDto.getPassword());
        user.setPhoneNumber(signupDto.getPhoneNumber());
        user.setDeleted(false);
        user.setRole("ADMIN");
        try {
            Optional<User> userInDatabase = userRepository.findUserByEmail(user.getEmail());
            if (userInDatabase.isPresent()) {
                apiResponseMessages.setMessage(ConstantMessages.EXIST.getMessage());
                return new ResponseEntity<>(apiResponseMessages, HttpStatus.BAD_REQUEST);
            }
            userRepository.save(user);
            String token = jwtService.createToken(user.getEmail(), user.getRoleAsList());
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
            Optional<User> optionalUser = userRepository.findUserByEmailAndDeleted(authenticationDto.getUsername(), false);
            if (optionalUser.isEmpty()) {
                apiResponseMessages.setMessage(ConstantMessages.INCORRECT.getMessage());
                return new ResponseEntity<>(apiResponseMessages, HttpStatus.BAD_REQUEST);
            }
            User user = optionalUser.get();
            if (!authenticationDto.getPassword().equals(user.getPassword())) {
                apiResponseMessages.setMessage(ConstantMessages.INCORRECT.getMessage());
                return new ResponseEntity<>(apiResponseMessages, HttpStatus.BAD_REQUEST);
            }
            String token = jwtService.createToken(user.getEmail(), user.getRoleAsList());
            apiResponseMessages.setData(token);
            apiResponseMessages.setMessage(ConstantMessages.USER_LOGGED_IN_SUCCESSFULLY.getMessage());
            return new ResponseEntity<>(apiResponseMessages, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ResponseEntity<>(apiResponseMessages, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @Override
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        ApiResponseMessages<String> apiResponseMessages = new ApiResponseMessages<>();
        apiResponseMessages.setMessage(ConstantMessages.FAILED.getMessage());
        return new ResponseEntity<>(apiResponseMessages, HttpStatus.INTERNAL_SERVER_ERROR);
        }
}