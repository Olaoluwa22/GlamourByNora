package com.GlamourByNora.api.constants;

import com.GlamourByNora.api.dto.UserResponseDto;
import com.GlamourByNora.api.model.User;
import org.springframework.stereotype.Component;

@Component
public class ConstantMethod {
    public UserResponseDto convertUserDto(User user){
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setFirstname(user.getFirstname());
        userResponseDto.setLastname(user.getLastname());
        userResponseDto.setCountry(user.getCountry());
        userResponseDto.setState(user.getState());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setPhone_no(user.getPhone_no());
        return userResponseDto;
    }
}
