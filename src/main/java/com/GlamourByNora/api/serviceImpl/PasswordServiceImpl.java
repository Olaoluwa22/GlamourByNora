package com.GlamourByNora.api.serviceImpl;

import com.GlamourByNora.api.dto.ForgetPasswordDto;
import com.GlamourByNora.api.dto.NewPasswordDto;
import com.GlamourByNora.api.dto.PasswordDto;
import com.GlamourByNora.api.dto.VerificationCodeDto;
import com.GlamourByNora.api.model.OTP;
import com.GlamourByNora.api.model.User;
import com.GlamourByNora.api.repository.OTPRepository;
import com.GlamourByNora.api.repository.UserRepository;
import com.GlamourByNora.api.response.ApiResponseMessages;
import com.GlamourByNora.api.service.EmailVerificationService;
import com.GlamourByNora.api.service.PasswordService;
import com.GlamourByNora.api.service.UserService;
import com.GlamourByNora.api.util.ConstantMessages;
import com.GlamourByNora.api.util.GetCookieValue;
import com.GlamourByNora.api.util.InfoGetter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class PasswordServiceImpl implements PasswordService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private OTPRepository otpRepository;
    @Autowired
    private EmailVerificationService emailVerificationService;
    @Autowired
    private UserService userService;
    @Autowired
    private InfoGetter infoGetter;
    @Override
    public ResponseEntity<?> updatePassword(PasswordDto passwordDto, HttpServletRequest request) {
        ApiResponseMessages<String> apiResponseMessages = new ApiResponseMessages<>();
        apiResponseMessages.setMessage(ConstantMessages.FAILED.getMessage());
        try {
            User user = infoGetter.getUser(userService.getUsernameOfLoggedInUser());
            if (!user.getPassword().equals(passwordDto.getOldPassword())){
                apiResponseMessages.setMessage(ConstantMessages.INCORRECT_OLD_PASSWORD.getMessage());
                return new ResponseEntity<>(apiResponseMessages, HttpStatus.BAD_REQUEST);
            }
            if (passwordDto.getOldPassword().equals(passwordDto.getNewPassword())){
                apiResponseMessages.setMessage(ConstantMessages.INPUT_A_NEW_PASSWORD.getMessage());
                return new ResponseEntity<>(apiResponseMessages, HttpStatus.BAD_REQUEST);
            }
            if (!passwordDto.getNewPassword().equals(passwordDto.getConfirmNewPassword())){
                apiResponseMessages.setMessage(ConstantMessages.CHECK_INPUT.getMessage());
                return new ResponseEntity<>(apiResponseMessages, HttpStatus.BAD_REQUEST);
            }
            user.setPassword(passwordDto.getConfirmNewPassword());
            userRepository.save(user);
            apiResponseMessages.setMessage(ConstantMessages.NEW_PASSWORD_SAVED.getMessage());
            return new ResponseEntity<>(apiResponseMessages, HttpStatus.OK);
        }catch(NullPointerException exception){
            exception.getMessage();
        }
        return new ResponseEntity<>(apiResponseMessages, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @Override
    public ResponseEntity<?> forgetPasswordAndSendOtp(ForgetPasswordDto forgetPasswordDto, HttpServletResponse response) {
        ApiResponseMessages<String> apiResponseMessages = new ApiResponseMessages<>();
        apiResponseMessages.setMessage(ConstantMessages.FAILED.getMessage());
        try {
            User user = infoGetter.getUser(forgetPasswordDto.getEmail());
            Cookie cookie = new Cookie("userEmail", user.getEmail());
            cookie.setMaxAge(600);
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            response.addCookie(cookie);
            OTP otp = new OTP();
            otp.setOtp(String.valueOf(ThreadLocalRandom.current().nextInt(100000, 999999)));
            otp.setCreatedAt(Instant.now());
            otp.setExpiresAt(Instant.now().plusSeconds(70));
            otp.setUserId(user.getId());
            otp.setExpired(false);
            emailVerificationService.sendOTP(user.getEmail(), otp.getOtp());
            otpRepository.save(otp);
        }catch (BadCredentialsException exception){
            exception.getMessage();
        }
        apiResponseMessages.setMessage(ConstantMessages.PROCEED.getMessage());
        return new ResponseEntity<>(apiResponseMessages, HttpStatus.OK);
    }
    public ResponseEntity<?> verifyOtp(VerificationCodeDto verificationCodeDto, HttpServletRequest request){
        ApiResponseMessages<String> apiResponseMessages = new ApiResponseMessages<>();
        try {
            OTP otp = infoGetter.getOtp(verificationCodeDto.getCode());
            if (Instant.now().isAfter(otp.getExpiresAt())){
                otp.setExpired(true);
                otpRepository.save(otp);
                apiResponseMessages.setMessage(ConstantMessages.OTP_IS_EXPIRED.getMessage());
                return new ResponseEntity<>(apiResponseMessages, HttpStatus.FORBIDDEN);
            }
            otp.setExpired(true);
            otpRepository.save(otp);
            apiResponseMessages.setMessage(ConstantMessages.PROCEED.getMessage());
            return new ResponseEntity<>(apiResponseMessages, HttpStatus.OK);
        }catch (BadCredentialsException exception){
            exception.printStackTrace();
        }
        apiResponseMessages.setMessage(ConstantMessages.FAILED.getMessage());
        return new ResponseEntity<>(apiResponseMessages, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @Override
    public ResponseEntity<?> resendCode(HttpServletRequest request) {
        ApiResponseMessages<String> apiResponseMessages = new ApiResponseMessages<>();
        apiResponseMessages.setMessage(ConstantMessages.FAILED.getMessage());
        GetCookieValue cookieValue = new GetCookieValue();
        try {
            User user = infoGetter.getUser(cookieValue.getCookieValue(request));
            OTP otp = new OTP();
            otp.setOtp(String.valueOf(ThreadLocalRandom.current().nextInt(100000, 999999)));
            otp.setCreatedAt(Instant.now());
            otp.setExpiresAt(Instant.now().plusSeconds(70));
            otp.setUserId(user.getId());
            otp.setExpired(false);
            emailVerificationService.sendOTP(user.getEmail(), otp.getOtp());
            otpRepository.save(otp);
            apiResponseMessages.setMessage(ConstantMessages.NEW_OTP_SENT.getMessage());
            return new ResponseEntity<>(apiResponseMessages, HttpStatus.OK);
        } catch (NullPointerException exception) {
            exception.printStackTrace();
        }
        return new ResponseEntity<>(apiResponseMessages, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @Override
    public ResponseEntity<?> setNewPassword(NewPasswordDto newPasswordDto, HttpServletRequest request) {
        ApiResponseMessages<String> apiResponseMessages = new ApiResponseMessages<>();
        apiResponseMessages.setMessage(ConstantMessages.FAILED.getMessage());
        GetCookieValue cookieValue = new GetCookieValue();
        try {
            User user = infoGetter.getUser(cookieValue.getCookieValue(request));
            if (!newPasswordDto.getNewPassword().equals(newPasswordDto.getConfirmNewPassword())){
                apiResponseMessages.setMessage(ConstantMessages.CHECK_INPUT.getMessage());
                return new ResponseEntity<>(apiResponseMessages, HttpStatus.BAD_REQUEST);
            }
            user.setPassword(newPasswordDto.getNewPassword());
            userRepository.save(user);
            apiResponseMessages.setMessage(ConstantMessages.NEW_PASSWORD_SAVED.getMessage());
            return new ResponseEntity<>(apiResponseMessages, HttpStatus.OK);
        }catch (NullPointerException exception){
            exception.getMessage();
        }
        return new ResponseEntity<>(apiResponseMessages, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}