package com.GlamourByNora.api.serviceImpl;

import com.GlamourByNora.api.exception.exceptionHandler.UserNotFoundException;
import com.GlamourByNora.api.exception.exceptionHandler.UserNotLoggedInException;
import com.GlamourByNora.api.model.User;
import com.GlamourByNora.api.repository.UserRepository;
import com.GlamourByNora.api.service.SessionAuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SessionAuthenticationServiceImpl implements SessionAuthenticationService {

    private UserRepository userRepository;
    public SessionAuthenticationServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }
    @Value("${app.session.login}")
    private String sessionCookieName;
    @Override
    public void login(User user, HttpServletRequest request) {
        HttpSession session = request.getSession();
        session.setAttribute("session", user.getEmail());
        session.setAttribute("firstname", user.getFirstName());
    }
    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new UserNotLoggedInException("User not Logged In");
        }
        session.invalidate();
    }
    @Override
    public void isUserLoggedIn(HttpServletRequest request) throws UserNotLoggedInException, UserNotFoundException {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new UserNotLoggedInException("User not Logged In");
        }
        Object loginSession = session.getAttribute(sessionCookieName);
        if (loginSession == null){
            throw new UserNotLoggedInException("User not Logged In");
        }
        String login = String.valueOf(loginSession);
        Optional<User> sessionOwner = userRepository.findUserByEmailAndDeleted(login, false);
        if (sessionOwner.isEmpty()){
            throw new UserNotFoundException("Unauthorized");
        }
    }
}