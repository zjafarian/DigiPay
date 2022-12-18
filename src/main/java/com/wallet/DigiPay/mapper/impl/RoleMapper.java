package com.wallet.DigiPay.mapper.impl;

import com.wallet.DigiPay.dto.RoleDto;
import com.wallet.DigiPay.dto.UserDto;
import com.wallet.DigiPay.dto.UserRequestDto;
import com.wallet.DigiPay.entities.Role;
import com.wallet.DigiPay.entities.User;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RoleMapper {

/*

    public Role toRole(UserRequestDto userRequestDto){
        User user = User.builder()
                .nationalCode(userRequestDto.getNationalCode())
                .phoneNumber(userRequestDto.getPhoneNumber())
                .build();

        user.setDateCreated(new Timestamp(System.currentTimeMillis()));
        user.setDateModified(new Timestamp(System.currentTimeMillis()));
        user.setDeleted(false);

        return user;
    }
*/

    public List<RoleDto> toRoleDtos(List<Role> roles){
        return roles.stream()
                .map(role -> new RoleDto(role.getId(),role.getDescription(),role.getRoleType()))
                .collect(Collectors.toList());
    }


}
