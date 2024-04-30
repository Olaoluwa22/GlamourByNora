package com.GlamourByNora.api.serviceImpl;

import com.GlamourByNora.api.dto.AdminDto;
import com.GlamourByNora.api.exception.exceptionHandler.EmailAlreadyExistException;
import com.GlamourByNora.api.model.User;
import com.GlamourByNora.api.repository.UserRepository;
import com.GlamourByNora.api.response.ApiResponseMessages;
import com.GlamourByNora.api.service.AdminProfileService;
import com.GlamourByNora.api.util.ConstantMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminProfileServiceImpl implements AdminProfileService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Override
    public ResponseEntity<?> createAdminProfile(AdminDto adminDto) {
        ApiResponseMessages<String> apiResponseMessages = new ApiResponseMessages<>();
        apiResponseMessages.setMessage(ConstantMessages.FAILED.getMessage());
        User user = new User();
        user.setFirstName(adminDto.getFirstname());
        user.setLastName(adminDto.getLastname());
        user.setCountry(adminDto.getCountry());
        user.setState(adminDto.getState());
        user.setAddress(adminDto.getAddress());
        user.setEmail(adminDto.getEmail());
        user.setPassword(passwordEncoder.encode(adminDto.getPassword()));
        user.setPhoneNumber(adminDto.getPhoneNumber());
        user.setRole("Admin");
        try {
            Optional<User> optionalUser = userRepository.findUserByEmail(user.getEmail());
            if (optionalUser.isPresent()) {
                apiResponseMessages.setMessage(ConstantMessages.EXIST.getMessage());
                return new ResponseEntity<>(apiResponseMessages, HttpStatus.BAD_REQUEST);
            }
            userRepository.save(user);
            apiResponseMessages.setMessage(ConstantMessages.ADMIN_PROFILE_CREATED.getMessage());
            return new ResponseEntity<>(apiResponseMessages, HttpStatus.CREATED);
        }catch (EmailAlreadyExistException ex){
            ex.printStackTrace();
        }
        return new ResponseEntity<>(apiResponseMessages, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    @Override
    public List<User> getAdminById(Long id) {
        return userRepository.findUserByIdAndRole(id, "Admin");
    }
    @Override
    public List<User> getAllAdmins() {
        return userRepository.findUserByRole("Admin");
    }
    @Override
    public Page<User> getAdminByPageable(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return userRepository.findUserByRole("Admin", pageable);
    }
}