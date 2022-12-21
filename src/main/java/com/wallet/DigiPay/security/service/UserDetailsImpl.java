package com.wallet.DigiPay.security.service;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.wallet.DigiPay.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String nationalCode;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    public UserDetailsImpl(Long id, String nationalCode, String password,
                           Collection<? extends GrantedAuthority> authorities) {
        this.id = id;
        this.nationalCode = nationalCode;
        this.password = password;
        this.authorities = authorities;
    }

    public static UserDetailsImpl build(User user) {

        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(user.getRole().getRoleType().toString());

        return new UserDetailsImpl(
                user.getId(),
                user.getNationalCode(),
                user.getPassword(),
                Arrays.asList(authority));
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return nationalCode;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsImpl user = (UserDetailsImpl) o;
        return Objects.equals(id, user.id);
    }
}
