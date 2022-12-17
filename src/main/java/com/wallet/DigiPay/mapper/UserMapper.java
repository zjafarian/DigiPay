package com.wallet.DigiPay.mapper;

import com.wallet.DigiPay.dto.UserDto;
import com.wallet.DigiPay.dto.UserRequestDto;
import com.wallet.DigiPay.entities.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    

    public User toUser(UserRequestDto userRequestDto){
        User user = User.builder()
                .nationalCode(userRequestDto.getNationalCode())
                .phoneNumber(userRequestDto.getPhoneNumber())
                .build();
        return user;
    }

    public UserDto toUserDto(User user){
        return null;
    }


}
