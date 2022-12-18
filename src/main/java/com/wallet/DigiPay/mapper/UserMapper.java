package com.wallet.DigiPay.mapper;

import com.wallet.DigiPay.dto.RoleDto;
import com.wallet.DigiPay.dto.UserDto;
import com.wallet.DigiPay.dto.UserRequestDto;
import com.wallet.DigiPay.entities.Role;
import com.wallet.DigiPay.entities.RoleDetail;
import com.wallet.DigiPay.entities.User;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class UserMapper {


    public User toUser(UserRequestDto userRequestDto) {
        User user = User.builder()
                .nationalCode(userRequestDto.getNationalCode())
                .phoneNumber(userRequestDto.getPhoneNumber())
                .password(userRequestDto.getPassword())
                .build();

        if (userRequestDto.getId() == null) {
            user.setDateCreated(new Timestamp(System.currentTimeMillis()));
            user.setDeleted(false);
        }
        if (userRequestDto.getName() != null)
            user.setName(userRequestDto.getName());

        if (userRequestDto.getLastName() != null)
            user.setLastName(userRequestDto.getLastName());

        user.setDateModified(new Timestamp(System.currentTimeMillis()));


        if (userRequestDto.getRoles().size() != 0 && userRequestDto.getRoles() != null) {
            user.setRoleDetails(userRequestDto
                    .getRoles()
                    .stream()
                    .map(roleDto -> {
                        Role roleGenerate = Role.builder()
                                .roleType(roleDto.getRoleType())
                                .description(roleDto.getDescription()).build();

                        roleGenerate.setId(roleDto.getId());
                        return  new RoleDetail(roleGenerate, user);
                    })
                    .collect(Collectors.toSet()));
        }


        return user;
    }


    public UserDto toUserDto(User user, List<RoleDto> roleDtos) {

        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .lastName(user.getLastName())
                .phoneNumber(user.getPhoneNumber())
                .nationalCode(user.getNationalCode())
                .password(user.getPassword())
                .roles(roleDtos).build();
    }


}
