package com.wallet.DigiPay.controller;



import com.wallet.DigiPay.dto.UserRequestDto;
import com.wallet.DigiPay.entities.User;
import com.wallet.DigiPay.exceptions.ExistNationalCodeException;
import com.wallet.DigiPay.exceptions.NationalCodeException;
import com.wallet.DigiPay.messages.ResponseMessage;
import com.wallet.DigiPay.security.*;
import com.wallet.DigiPay.services.impls.AuthService;
import com.wallet.DigiPay.services.impls.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Validated
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserServiceImpl userService;


    @GetMapping("/testOrigins")
    public String testOrigin(HttpServletRequest request, HttpServletResponse response){
        System.out.println("uri: "+ request.getRequestURI());
        System.out.println("url: "+ request.getRequestURL());

        return "tested Origins" ;
    }



    @PostMapping("/register")
    public ResponseEntity<ResponseMessage<?>> register(@Valid @RequestBody UserRequestDto userRequestDto)
            throws ConstraintViolationException,
            NullPointerException,
            ExistNationalCodeException,
            NationalCodeException {

        //registering user
        User user = authService.register(userRequestDto);


        //create response
        ResponseMessage responseMessage = ResponseMessage
                .withResponseData(user,
                        "user Created Successfully",
                        "message");

        return new ResponseEntity<ResponseMessage<?>>(responseMessage, HttpStatus.CREATED);

    }



    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest, HttpServletResponse response){

        //create LoginTokens from loginRequest and response
        LoginTokens loginTokens =  authService.login(loginRequest.getNationalCode(),
                loginRequest.getPassword(),response) ;

        //create LoginResponse and return it
        return new LoginResponse(loginTokens.getAccessToken().getToken());
    }


    //get user with token
    @GetMapping("/user")
    public ResponseEntity<ResponseMessage<?>> findUserFromToken(HttpServletRequest request){

        //get user with request
        User user = (User) request.getAttribute("user");

        System.out.println("user : " + user);


        //create userRequestDto with User
        UserRequestDto  userRequestDto= userService.generateUserRequestDto(user);



        //create response
        ResponseMessage responseMessage = ResponseMessage
                .withResponseData(userRequestDto,
                        "user find successful",
                        "message");


        return new ResponseEntity<ResponseMessage<?>>(responseMessage, HttpStatus.ACCEPTED);
    }


    @GetMapping("/refresh")
    public RefreshResponse newAccessTokenFromRefreshToken(@CookieValue("refresh_token") String refreshToken){

        //create token for saving in refresh token
        LoginTokens tokens = authService.reNewAccessToken(refreshToken);
            String accessToken = tokens.getAccessToken().getToken();

        return new RefreshResponse(accessToken) ;
    }


    @GetMapping("/logout")
    public LogoutResponse logout(HttpServletResponse response){
        authService.logout(response);

        return new LogoutResponse("success") ;
    }

}

