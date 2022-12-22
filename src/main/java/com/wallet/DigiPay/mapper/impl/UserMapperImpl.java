package com.wallet.DigiPay.mapper.impl;


import com.wallet.DigiPay.dto.UserRequestDto;

import com.wallet.DigiPay.entities.User;
import com.wallet.DigiPay.mapper.UserMapper;
import org.springframework.stereotype.Component;



@Component
public class UserMapperImpl extends BaseMapperImpl<UserRequestDto, User> implements UserMapper {



    @Override
    public UserRequestDto mapToDTO(User user) {
        UserRequestDto userRequestDto = new UserRequestDto();
        if (user.getId() != null)
            userRequestDto.setId(user.getId());

        if (!user.getPassword().isEmpty())
            userRequestDto.setPassword(user.getPassword());

        if (!user.getPhoneNumber().isEmpty())
            userRequestDto.setPhoneNumber(user.getPhoneNumber());

        if (!user.getNationalCode().isEmpty())
            userRequestDto.setNationalCode(user.getNationalCode());

        if (user.getRole() != null)
            userRequestDto.setRoleId(user.getRole().getId());



        userRequestDto.setName(user.getName());
        userRequestDto.setLastName(user.getLastName());







            return userRequestDto;
    }

    @Override
    public User mapToObject(UserRequestDto userRequestDto) {

        User user = new User();
        user.setNationalCode(userRequestDto.getNationalCode());
        user.setPhoneNumber(userRequestDto.getPhoneNumber());
        user.setPassword(userRequestDto.getPassword());


        if (userRequestDto.getId() == null) {
            user.setDeleted(false);
        }
        if (userRequestDto.getName() != null)
            user.setName(userRequestDto.getName());

        if (userRequestDto.getLastName() != null)
            user.setLastName(userRequestDto.getLastName());




        return user;

    }
}
