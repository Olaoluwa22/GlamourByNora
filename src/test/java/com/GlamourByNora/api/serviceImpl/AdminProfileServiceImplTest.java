package com.GlamourByNora.api.serviceImpl;

import com.GlamourByNora.api.dto.AdminDto;
import com.GlamourByNora.api.exception.exceptionHandler.EmailAlreadyExistException;
import com.GlamourByNora.api.model.User;
import com.GlamourByNora.api.repository.UserRepository;
import com.GlamourByNora.api.response.ApiResponseMessages;
import com.GlamourByNora.api.util.ConstantMessages;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AdminProfileServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private AdminProfileServiceImpl adminProfileService;

    @Test
    void adminProfileService_CreateAdminProfile_ReturnsHttpStatus_Created(){
        //Arrange
        AdminDto adminDto = new AdminDto();
        adminDto.setFirstname("Admin");
        adminDto.setLastname("Profile");
        adminDto.setCountry("Nigeria");
        adminDto.setState("Lagos");
        adminDto.setAddress("123 Admin Street");
        adminDto.setEmail("admin@example.com");
        adminDto.setPassword("encodedPassword");
        adminDto.setPhoneNumber(9012345678L);

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
        ApiResponseMessages<String> responseMessages = new ApiResponseMessages<>();
        ResponseEntity<?> responseEntity = new ResponseEntity<>(responseMessages.setMessage(ConstantMessages.CREATED.getMessage()), HttpStatus.CREATED);

        //Act
        when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        ResponseEntity<?> response = adminProfileService.createAdminProfile(adminDto);

        //Assert
        Assertions.assertThat(response.getStatusCode()).isEqualTo(responseEntity.getStatusCode());

    }
    @Test
    void adminProfileService_CreateAdminProfile_ReturnsHttpStatus_BadRequest(){
        //Arrange
        AdminDto adminDto = new AdminDto();
        adminDto.setFirstname("Admin");
        adminDto.setLastname("Profile");
        adminDto.setCountry("Nigeria");
        adminDto.setState("Lagos");
        adminDto.setAddress("123 Admin Street");
        adminDto.setEmail("admin@example.com");
        adminDto.setPassword("encodedPassword");
        adminDto.setPhoneNumber(9012345678L);

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
        ApiResponseMessages<String> responseMessages = new ApiResponseMessages<>();
        ResponseEntity<?> responseEntity = new ResponseEntity<>(responseMessages.setMessage(ConstantMessages.BAD_REQUEST.getMessage()), HttpStatus.BAD_REQUEST);

        //Act
        when(userRepository.findUserByEmail(Mockito.any(String.class))).thenReturn(Optional.of(new User()));
        ResponseEntity<?> response = adminProfileService.createAdminProfile(adminDto);

        //Assert
        Assertions.assertThat(response.getStatusCode()).isEqualTo(responseEntity.getStatusCode());

    }
    @Test
    void adminProfileService_CreateAdminProfile_ReturnsHttpStatus_InternalServerError(){
        //Arrange
        AdminDto adminDto = new AdminDto();
        adminDto.setFirstname("Admin");
        adminDto.setLastname("Profile");
        adminDto.setCountry("Nigeria");
        adminDto.setState("Lagos");
        adminDto.setAddress("123 Admin Street");
        adminDto.setEmail("admin@example.com");
        adminDto.setPassword("encodedPassword");
        adminDto.setPhoneNumber(9012345678L);

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
        ApiResponseMessages<String> responseMessages = new ApiResponseMessages<>();
        ResponseEntity<?> responseEntity =
                new ResponseEntity<>(responseMessages.setMessage(ConstantMessages.INTERNAL_SERVER_ERROR.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);

        //Act
        when(userRepository.findUserByEmail(Mockito.any(String.class))).thenThrow(new EmailAlreadyExistException("Email Exists"));
        ResponseEntity<?> response = adminProfileService.createAdminProfile(adminDto);

        //Assert
        Assertions.assertThat(response.getStatusCode()).isEqualTo(responseEntity.getStatusCode());

    }
    
}