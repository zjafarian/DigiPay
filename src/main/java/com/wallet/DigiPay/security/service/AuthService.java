package com.wallet.DigiPay.security.service;

import com.wallet.DigiPay.dto.UserRequestDto;
import com.wallet.DigiPay.entities.Role;
import com.wallet.DigiPay.entities.User;
import com.wallet.DigiPay.exceptions.NotFoundException;
import com.wallet.DigiPay.exceptions.PasswordException;
import com.wallet.DigiPay.mapper.impl.UserMapperImpl;
import com.wallet.DigiPay.messages.ErrorMessages;
import com.wallet.DigiPay.repositories.RoleRepository;
import com.wallet.DigiPay.repositories.UserRepository;
import com.wallet.DigiPay.security.jwt.JwtUtils;
import com.wallet.DigiPay.security.models.LoginRequest;
import com.wallet.DigiPay.security.models.LoginResponse;
import com.wallet.DigiPay.security.service.error.JWTIsExpired;
import com.wallet.DigiPay.services.UserService;
import com.wallet.DigiPay.utils.PasswordValidation;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AuthService {

    private ErrorMessages errorMessages;
    private PasswordEncoder encoder;
    private RoleRepository roleRepository;
    private UserMapperImpl userMapper;
    private JwtUtils jwtUtils;
    private UserRepository userRepository;


    public AuthService(ErrorMessages errorMessages,
                       UserRepository userRepository,
                       RoleRepository roleRepository,
                       UserMapperImpl userMapper,
                       PasswordEncoder encoder,
                       JwtUtils jwtUtils) {

        this.errorMessages = errorMessages;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.encoder = encoder;
        this.userMapper = userMapper;
        this.jwtUtils = jwtUtils;
    }

    public User registerUser(UserRequestDto userRequestDto){
        //check password is valid or not
        if (!PasswordValidation.validationPassword(userRequestDto.getPassword()))
            throw new PasswordException(errorMessages.getMESSAGE_PASSWORD_NOT_VALID());

        userRequestDto.setPassword(encoder.encode(userRequestDto.getPassword()));
        Role role = roleRepository.findById(userRequestDto.getRoleId()).get();
        User user = userMapper.mapToObject(userRequestDto);
        user.setRole(role);

        User userSave = userRepository.save(user);

        return userSave;

    }


    public LoginResponse login(Authentication authentication, LoginRequest loginRequest){


        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        LoginResponse loginResponse = new LoginResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                roles.get(0));

        jwtUtils.setRefreshToken(loginResponse);



        return loginResponse;

    }

    public UserRequestDto getUser(HttpServletRequest request){

        String nationalCode = "";
        String token = request.getHeader("Authorization");
        token = token.split(" ")[1].trim();

        if (jwtUtils.validateJwtToken(token))
            nationalCode = jwtUtils.getUserNameFromJwtToken(token);


        Optional<User> user = userRepository.findByNationalCode(nationalCode);

        if (!user.isPresent())
            throw new NotFoundException(errorMessages.getMESSAGE_NOT_FOUND_USER());


        //create userRequestDto with User
        UserRequestDto userRequestDto = userMapper.mapToDTO(user.get());
        userRequestDto.setPassword(null);


        return userRequestDto;



    }

    public User getUserFromAccessToken(String token){
        String nationalCode = "";
        try {
            if (jwtUtils.validateJwtToken(token))
                nationalCode = jwtUtils.getUserNameFromJwtToken(token);

        }
        catch (ExpiredJwtException exception){
            throw new JWTIsExpired("access token is expired");
        }

        return userRepository.findByNationalCode(nationalCode)
                .orElseThrow(()-> new NotFoundException(errorMessages.getMESSAGE_NOT_FOUND_USER()));


    }
}
