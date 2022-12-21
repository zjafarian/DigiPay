package com.wallet.DigiPay.services.impls;


import com.wallet.DigiPay.dto.UserRequestDto;
import com.wallet.DigiPay.entities.Role;
import com.wallet.DigiPay.entities.User;
import com.wallet.DigiPay.exceptions.authException.JWTIsExpired;
import com.wallet.DigiPay.exceptions.authException.UserNotFoundError;
import com.wallet.DigiPay.repositories.RoleRepository;
import com.wallet.DigiPay.security.LoginTokens;
import com.wallet.DigiPay.security.Token;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Service
public class AuthService {


    private UserServiceImpl userService;
    private PasswordEncoder passwordEncoder;
    private RoleRepository roleRepository;

    @Autowired

    public AuthService( UserServiceImpl userService,
                        PasswordEncoder passwordEncoder,
                        RoleRepository roleRepository) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }



    @Value("${access.secret.key}")
    private String accessSecretKey;

    @Value("${refresh.secret.key}")
    private String refreshSecretKey;

    @Value("${access.secret.key.validityInMinutes}")
    private Long accessSecretKeyValidityInMinutes;

    @Value("${refresh.secret.key.validityInMinutes}")
    private Long refreshSecretKeyValidityInMinutes;

    @Value("${server.servlet.context-path}")
    private String contextPath;


    public User register(UserRequestDto userRequestDto) {

        Role role = roleRepository.findById(userRequestDto.getRoleId()).get();


        //check the validity of data
        User user = new User();
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        user.setNationalCode(userRequestDto.getNationalCode());
        user.setPhoneNumber(userRequestDto.getPhoneNumber());
        user.setRole(role);

        return userService.save(user);
    }

    public LoginTokens login(String nationalCode, String password, HttpServletResponse response) {


        User user = userService.findUserByNationalCode(nationalCode).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.BAD_REQUEST, "nationalCode or password is wrong"));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "username or password is wrong");
        }


        LoginTokens loginTokens = LoginTokens.of(nationalCode, accessSecretKey, accessSecretKeyValidityInMinutes, refreshSecretKey, refreshSecretKeyValidityInMinutes);

        Cookie cookie = new Cookie("refresh_token", loginTokens.getRefreshToken().getToken());
        cookie.setMaxAge(3600);
        cookie.setHttpOnly(true);
        cookie.setPath(contextPath);

        response.addCookie(cookie);

        return loginTokens;

    }


    public User getUserFromAccessToken(String token) {
        String userNationalCode = "";
        try {
            userNationalCode = Token.from(token, accessSecretKey);
        } catch (ExpiredJwtException exception) {
            throw new JWTIsExpired("access token is expired");
        }

        return userService.findUserByNationalCode(userNationalCode)
                .orElseThrow(UserNotFoundError::new);


    }

    public User getUserFromRefreshToken(String refreshToken) {
        String userNationalCode = "";
        try {
            userNationalCode = Token.from(refreshToken, refreshSecretKey);
        } catch (ExpiredJwtException exception) {
            throw new JWTIsExpired("refresh token is expired");
        }

        return userService.findUserByNationalCode(userNationalCode)
                .orElseThrow(UserNotFoundError::new);


    }

    public LoginTokens reNewAccessToken(String refreshToken) {
        User user = getUserFromRefreshToken(refreshToken);

        return LoginTokens.of(user.getNationalCode(), accessSecretKey, accessSecretKeyValidityInMinutes, refreshToken);

    }

    public void logout(HttpServletResponse response) {

        Cookie cookie = new Cookie("refresh_token", null);
        cookie.setMaxAge(0);
        cookie.setHttpOnly(true);
        cookie.setPath(contextPath);

        response.addCookie(cookie);
    }
}
