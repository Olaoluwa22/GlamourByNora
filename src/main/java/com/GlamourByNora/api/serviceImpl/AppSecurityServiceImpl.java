package com.GlamourByNora.api.serviceImpl;

import com.GlamourByNora.api.constants.ConstantMessages;
import com.GlamourByNora.api.model.User;
import com.GlamourByNora.api.repository.UserRepository;
import com.GlamourByNora.api.response.ApiResponseMessages;
import com.GlamourByNora.api.service.AppSecurityService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AppSecurityServiceImpl implements AppSecurityService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public ResponseEntity<?> getLoggedInUser(HttpServletRequest request) throws Exception {
        ApiResponseMessages<String> apiResponseMessages = new ApiResponseMessages<>();
        apiResponseMessages.setMessage(ConstantMessages.FAILED.getMessage());
        Cookie[] cookie = request.getCookies();
        boolean loggedIn = false;
        Cookie loginCookie = null;

        if (cookie == null){
            throw new Exception();
        }
        try{
            for (int i = 0; i < cookie.length ; i++) {
                Cookie cookie1 = cookie[i];
                if (cookie1.getName().equals("login")){
                    loggedIn = true;
                    loginCookie = cookie1;
                    break;
                }
            }
            if (!loggedIn){
                throw new Exception();
            }
            Optional<User> cookieOwner = userRepository.findByEmailAndDeleted(loginCookie.getValue(), false);
            if (cookieOwner.isEmpty()){
                throw new Exception();
            }
            User user = cookieOwner.get();
            apiResponseMessages.setMessage(ConstantMessages.SUCCESS.getMessage());
            apiResponseMessages.setData(user.toString());
            return new ResponseEntity<>(apiResponseMessages, HttpStatus.OK);
        }catch (Exception ex){
            ex.printStackTrace();
        }
        throw new Exception();
    }
}
