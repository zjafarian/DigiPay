package com.wallet.DigiPay.entities;

import lombok.Builder;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "t_user")
public class User extends BaseModel {


    @Column(name = "use_name")
    private String name;

    @Column(name = "use_lastname")
    private String lastName;

    @Column(name = "use_national_code")
    private String nationalCode;

    @Column(name = "use_phone_number")
    private String phoneNumber;

    @Column(name = "use_password")
    private String password;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "use_role_id")
    private Role role;



    public User() {

    }



    public User(String name, String lastName, String nationalCode, String phoneNumber,String password,Role role) {

        super.setDeleted(false);

        this.name = name;
        this.lastName = lastName;
        this.nationalCode = nationalCode;
        this.phoneNumber = phoneNumber;
        this.password = password;
        this.role = role;
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


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
