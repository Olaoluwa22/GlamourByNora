package com.GlamourByNora.api.serviceImpl;

import com.GlamourByNora.api.constants.ConstantMessages;
import com.GlamourByNora.api.controller.ForgetPasswordDto;
import com.GlamourByNora.api.controller.PasswordController;
import com.GlamourByNora.api.dto.NewPasswordDto;
import com.GlamourByNora.api.dto.PasswordDto;
import com.GlamourByNora.api.dto.VerificationCodeDto;
import com.GlamourByNora.api.model.User;
import com.GlamourByNora.api.repository.UserRepository;
import com.GlamourByNora.api.response.ApiResponseMessages;
import com.GlamourByNora.api.service.AppSecurityService;
import com.GlamourByNora.api.service.EmailVerificationService;
import com.GlamourByNora.api.service.PasswordService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class PasswordServiceImpl implements PasswordService {
    @Autowired
    private AppSecurityService appSecurityService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private EmailVerificationService emailVerificationService;
    @Value("${app.cookie.login}")
    private String loginCookieName;
    private final String verificationCode = String.valueOf(ThreadLocalRandom.current().nextInt(100000, 999999));
    @Override
    public ResponseEntity<?> updatePassword(PasswordDto passwordDto, HttpServletRequest request) {
        appSecurityService.getLoggedInUser(request);
        ApiResponseMessages<String> apiResponseMessages = new ApiResponseMessages<>();
        apiResponseMessages.setMessage(ConstantMessages.FAILED.getMessage());
        Cookie[] cookies = request.getCookies();
        Cookie loggedInUser = null;
        try {
            for (int i = 0; i < cookies.length; i++) {
                Cookie cookies1 = cookies[i];
                if (cookies1.getName().equalsIgnoreCase(loginCookieName)) {
                    loggedInUser = cookies1;
                    break;
                }
            }
            Optional<User> databaseUser = userRepository.findByEmail(loggedInUser.getValue());
            if (databaseUser.isEmpty()){
                apiResponseMessages.setMessage(ConstantMessages.FAILED.getMessage());
                return new ResponseEntity<>(apiResponseMessages, HttpStatus.INTERNAL_SERVER_ERROR);
            }
            User user = databaseUser.get();
            if (!user.getPassword().equals(passwordDto.getOldPassword())){
                apiResponseMessages.setMessage(ConstantMessages.INCORRECT_PASSWORD.getMessage());
                return new ResponseEntity<>(apiResponseMessages, HttpStatus.BAD_REQUEST);
            }
            if (!passwordDto.getNewPassword().equals(passwordDto.getConfirmNewPassword())){
                apiResponseMessages.setMessage(ConstantMessages.CHECK_INPUT.getMessage());
                return new ResponseEntity<>(apiResponseMessages, HttpStatus.BAD_REQUEST);
            }
            user.setPassword(passwordDto.getNewPassword());
            userRepository.save(user);
            apiResponseMessages.setMessage(ConstantMessages.SUCCESS.getMessage());
            return new ResponseEntity<>(apiResponseMessages, HttpStatus.OK);
        }catch(NullPointerException exception){
            exception.getMessage();
        }
        return new ResponseEntity<>(apiResponseMessages, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @Override
    public ResponseEntity<?> forgetPassword(ForgetPasswordDto forgetPasswordDto, HttpServletResponse response) {
        ApiResponseMessages<String> apiResponseMessages = new ApiResponseMessages<>();
        apiResponseMessages.setMessage(ConstantMessages.FAILED.getMessage());
        try {
            Optional<User> databaseUser = userRepository.findByEmail(forgetPasswordDto.getEmail());
            if (databaseUser.isEmpty()) {
                apiResponseMessages.setMessage(ConstantMessages.INVALID_EMAIL.getMessage());
                return new ResponseEntity<>(apiResponseMessages, HttpStatus.BAD_REQUEST);
            }
            Cookie cookie = new Cookie("userEmail", forgetPasswordDto.getEmail());
            cookie.setMaxAge(600);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.addCookie(cookie);
            User user = databaseUser.get();
            emailVerificationService.sendVerificationCode(user, verificationCode);
        }catch (NullPointerException exception){
            exception.getMessage();
        }
        apiResponseMessages.setMessage(ConstantMessages.SUCCESS.getMessage());
        return new ResponseEntity<>(apiResponseMessages, HttpStatus.OK);
    }
    public ResponseEntity<?> verifyCode(VerificationCodeDto verificationCodeDto){
        ApiResponseMessages<String> apiResponseMessages = new ApiResponseMessages<>();
        if (!verificationCodeDto.getCode().equals(verificationCode)){
            apiResponseMessages.setMessage(ConstantMessages.CHECK_INPUT.getMessage());
            return new ResponseEntity<>(apiResponseMessages, HttpStatus.BAD_REQUEST);
        }
        apiResponseMessages.setMessage(ConstantMessages.PROCEED.getMessage());
        return new ResponseEntity<>(apiResponseMessages, HttpStatus.OK);
    }
    @Override
    public ResponseEntity<?> setNewPassword(NewPasswordDto newPasswordDto, HttpServletRequest request) {
        ApiResponseMessages<String> apiResponseMessages = new ApiResponseMessages<>();
        apiResponseMessages.setMessage(ConstantMessages.FAILED.getMessage());
        Cookie[] cookie = request.getCookies();
        Cookie userEmail = null;
            for (int i = 0; i < cookie.length; i++) {
                Cookie cookies = cookie[i];
                if (cookies.getName().equalsIgnoreCase("userEmail")) {
                    userEmail = cookies;
                    cookies.setMaxAge(0);
                    break;
                }
            }
        try {
            Optional<User> databaseUser = userRepository.findByEmail(userEmail.getValue());
            if (databaseUser.isEmpty()) {
                apiResponseMessages.setMessage(ConstantMessages.INVALID_EMAIL.getMessage());
                return new ResponseEntity<>(apiResponseMessages, HttpStatus.BAD_REQUEST);
            }
            User user = databaseUser.get();

            if (!newPasswordDto.getPassword().equals(newPasswordDto.getConfirmPassword())){
                apiResponseMessages.setMessage(ConstantMessages.CHECK_INPUT.getMessage());
                return new ResponseEntity<>(apiResponseMessages, HttpStatus.BAD_REQUEST);
            }
            user.setPassword(newPasswordDto.getPassword());
            userRepository.save(user);
            apiResponseMessages.setMessage(ConstantMessages.SUCCESS.getMessage());
            return new ResponseEntity<>(apiResponseMessages, HttpStatus.OK);
        }catch (NullPointerException exception){
            exception.getMessage();
        }
        return new ResponseEntity<>(apiResponseMessages, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
