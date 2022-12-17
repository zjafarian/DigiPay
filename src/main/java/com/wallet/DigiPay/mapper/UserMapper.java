package com.wallet.DigiPay.mapper;

import com.wallet.DigiPay.dto.UserDto;
import com.wallet.DigiPay.dto.UserRequestDto;
import com.wallet.DigiPay.entities.User;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;

@Component
public class UserMapper {
    

    public User toUser(UserRequestDto userRequestDto){
        User user = User.builder()
                .nationalCode(userRequestDto.getNationalCode())
                .phoneNumber(userRequestDto.getPhoneNumber())
                .build();

        user.setDateCreated(new Timestamp(System.currentTimeMillis()));
        user.setDateModified(new Timestamp(System.currentTimeMillis()));
        user.setDeleted(false);

        return user;
    }

    public UserDto toUserDto(User user){
        return null;
    }


}
