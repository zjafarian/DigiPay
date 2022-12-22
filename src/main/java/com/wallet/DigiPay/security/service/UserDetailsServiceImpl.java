package com.wallet.DigiPay.security.service;


import com.wallet.DigiPay.entities.User;
import com.wallet.DigiPay.repositories.UserRepository;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String nationalCode) throws UsernameNotFoundException {
        User user = userRepository.findByNationalCode(nationalCode).get();
        if (user == null) {
            throw new UsernameNotFoundException("User Not Found with national code: " + nationalCode);
        }
        return UserDetailsImpl.build(user);
    }







}
