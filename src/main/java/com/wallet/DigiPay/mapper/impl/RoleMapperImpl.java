package com.wallet.DigiPay.mapper.impl;


import com.wallet.DigiPay.dto.RoleRequestDto;
import com.wallet.DigiPay.entities.Role;
import com.wallet.DigiPay.mapper.RoleMapper;
import org.springframework.stereotype.Component;


@Component
public class RoleMapperImpl extends BaseMapperImpl<RoleRequestDto, Role> implements RoleMapper {










    @Override
    public RoleRequestDto mapToDTO(Role role) {

        RoleRequestDto roleRequestDto = new RoleRequestDto();
        roleRequestDto.setId(role.getId());
        roleRequestDto.setRoleType(role.getRoleType());
        roleRequestDto.setRoleDescription(role.getDescription());

        return roleRequestDto;
    }

    @Override
    public Role mapToObject(RoleRequestDto roleRequestDto) {

        return new Role(roleRequestDto.getRoleDescription(),roleRequestDto.getRoleType());
    }
}
