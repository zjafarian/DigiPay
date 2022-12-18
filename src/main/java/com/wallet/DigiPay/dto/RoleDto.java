package com.wallet.DigiPay.dto;

import com.wallet.DigiPay.entities.Role;
import com.wallet.DigiPay.entities.RoleType;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;

public class RoleDto implements Serializable {



    private Long id;

    private String description;


    private RoleType roleType;


    public RoleDto() {
    }

    public RoleDto(Long id, String description, RoleType roleType) {
        this.id = id;
        this.description = description;
        this.roleType = roleType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RoleType getRoleType() {
        return roleType;
    }

    public void setRoleType(RoleType roleType) {
        this.roleType = roleType;
    }
}
