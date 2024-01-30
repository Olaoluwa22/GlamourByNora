package com.GlamourByNora.api.serviceImpl;

import com.GlamourByNora.api.constants.ConstantMessages;
import com.GlamourByNora.api.dto.PasswordDto;
import com.GlamourByNora.api.model.User;
import com.GlamourByNora.api.repository.UserRepository;
import com.GlamourByNora.api.response.ApiResponseMessages;
import com.GlamourByNora.api.service.AppSecurityService;
import com.GlamourByNora.api.service.PasswordService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PasswordServiceImpl implements PasswordService {
    @Autowired
    private AppSecurityService appSecurityService;
    @Autowired
    private UserRepository userRepository;
    @Value("${app.cookie.login}")
    private String loginCookieName;
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
}
