package com.GlamourByNora.api.util;

import com.GlamourByNora.api.dto.UserResponseDto;
import com.GlamourByNora.api.model.User;
import org.springframework.stereotype.Component;

@Component
public class ConstantMethod {
    public UserResponseDto convertUserDto(User user){
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setFirstName(user.getFirstName());
        userResponseDto.setLastName(user.getLastName());
        userResponseDto.setCountry(user.getCountry());
        userResponseDto.setState(user.getState());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setPhoneNumber(user.getPhoneNumber());
        return userResponseDto;
    }
}
