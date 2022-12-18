package com.wallet.DigiPay.dto;

import com.wallet.DigiPay.entities.Role;
import org.springframework.validation.annotation.Validated;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Validated
public class UserRequestDto {

    private Long id;

    @Size(min = 10, max = 10,message = "size of phoneNumber isn't correct!")
    @NotBlank
    private String nationalCode;

    @Size(min = 11, max = 11,message = "size of national code isn't correct!")
    @NotBlank
    private String phoneNumber;

    @NotBlank
    private String password;

    private String name;

    private String lastName;

    @NotNull
    private List<RoleDto> roles;

    public UserRequestDto() {
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public List<RoleDto> getRoles() {
        return roles;
    }

    public void setRoles(List<RoleDto> roles) {
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
