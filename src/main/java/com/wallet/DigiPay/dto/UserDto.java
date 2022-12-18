package com.wallet.DigiPay.dto;

import com.wallet.DigiPay.entities.Role;
import com.wallet.DigiPay.entities.RoleDetail;
import lombok.Builder;
import org.springframework.validation.annotation.Validated;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Builder

public class UserDto implements Serializable {


    private Long id;

    private String name;

    private String lastName;

    private String nationalCode;

    private String phoneNumber;

    private String password;

    private List<RoleDto> roles;


    public UserDto() {
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public  List<RoleDto>  getRoles() {
        return roles;
    }

    public void setRoles( List<RoleDto>  roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
