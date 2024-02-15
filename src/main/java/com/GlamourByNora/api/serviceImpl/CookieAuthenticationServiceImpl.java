package com.GlamourByNora.api.serviceImpl;

import com.GlamourByNora.api.constants.ConstantMessages;
import com.GlamourByNora.api.constants.ConstantMethod;
import com.GlamourByNora.api.dto.UserResponseDto;
import com.GlamourByNora.api.exception.exceptionHandler.UserNotLoggedInException;
import com.GlamourByNora.api.model.User;
import com.GlamourByNora.api.repository.UserRepository;
import com.GlamourByNora.api.response.ApiResponseMessages;
import com.GlamourByNora.api.service.CookieAuthenticationService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CookieAuthenticationServiceImpl implements CookieAuthenticationService {
    private UserRepository userRepository;
    private ConstantMethod constantMethod;
    public CookieAuthenticationServiceImpl(UserRepository userRepository, ConstantMethod constantMethod){
        this.userRepository = userRepository;
        this.constantMethod = constantMethod;
    }
    @Value("${app.cookie.login}")
    private String loginCookieName;
    @Override
    public void login(User user, HttpServletResponse response) {
        Cookie cookie = new Cookie(loginCookieName, user.getEmail());
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(600);
        response.addCookie(cookie);
    }
    @Override
    public ResponseEntity<?> logout(HttpServletRequest request, HttpServletResponse response) {
        ApiResponseMessages<String> apiResponseMessages = new ApiResponseMessages<>();
        apiResponseMessages.setMessage(ConstantMessages.FAILED.getMessage());
        try {
            Cookie[] cookie = request.getCookies();
            if (cookie == null) {
                apiResponseMessages.setMessage(ConstantMessages.UNAUTHORIZED.getMessage());
                return new ResponseEntity<>(apiResponseMessages, HttpStatus.UNAUTHORIZED);
            }
            for (int i = 0; i < cookie.length; i++) {
                Cookie loginCookie = cookie[i];
                if (loginCookie.getName().equalsIgnoreCase(loginCookieName)) {
                    loginCookie.setMaxAge(0);
                    loginCookie.setPath("/");
                    response.addCookie(loginCookie);
                    break;
                }
            }
            apiResponseMessages.setMessage(ConstantMessages.LOGGED_OUT.getMessage());
            return new ResponseEntity<>(apiResponseMessages, HttpStatus.OK);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return new ResponseEntity<>(apiResponseMessages, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @Override
    public ResponseEntity<?> getLoggedInUser(HttpServletRequest request) {
        ApiResponseMessages<String> apiResponseMessages = new ApiResponseMessages<>();
        apiResponseMessages.setMessage(ConstantMessages.FAILED.getMessage());
        Cookie[] cookie = request.getCookies();
        boolean loggedIn = false;
        Cookie loginCookie = null;

        if (cookie == null){
            throw new UserNotLoggedInException("User not Logged In...");
        }
        try{
            for (int i = 0; i < cookie.length ; i++) {
                Cookie cookie1 = cookie[i];
                if (cookie1.getName().equalsIgnoreCase(loginCookieName)){
                    loggedIn = true;
                    loginCookie = cookie1;
                    break;
                }
            }
            if (!loggedIn){
                throw new UserNotLoggedInException("User not Logged In...");
            }
            Optional<User> cookieOwner = userRepository.findByEmailAndDeleted(loginCookie.getValue(), false);
            if (cookieOwner.isEmpty()){
                throw new UserNotLoggedInException("User not Logged In...");
            }
            apiResponseMessages.setMessage(ConstantMessages.SUCCESS.getMessage());
            return new ResponseEntity<>(apiResponseMessages, HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        throw new UserNotLoggedInException("User not Logged In...");
    }
}