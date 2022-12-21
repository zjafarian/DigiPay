package com.wallet.DigiPay.controller;



import com.wallet.DigiPay.dto.UserRequestDto;
import com.wallet.DigiPay.entities.User;
import com.wallet.DigiPay.messages.ResponseMessage;
import com.wallet.DigiPay.security.*;
import com.wallet.DigiPay.services.impls.AuthService;
import com.wallet.DigiPay.services.impls.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
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
    public ResponseEntity<ResponseMessage<?>> register(@RequestBody UserRequestDto userRequestDto) {
        User user = authService.register(userRequestDto);


        ResponseMessage responseMessage = ResponseMessage
                .withResponseData(user,
                        "user Created Successfully",
                        "message");

        return new ResponseEntity<ResponseMessage<?>>(responseMessage, HttpStatus.CREATED);

    }



    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest, HttpServletResponse response){
        LoginTokens loginTokens =  authService.login(loginRequest.getNationalCode(),loginRequest.getPassword(),response) ;

        return new LoginResponse(loginTokens.getAccessToken().getToken());
    }


    @GetMapping("/user")
    public ResponseEntity<ResponseMessage<?>> findUserFromToken(HttpServletRequest request){

        User user = (User) request.getAttribute("user");

        System.out.println("user : " + user);


        UserRequestDto  userRequestDto= userService.generateUserRequestDto(user);



        ResponseMessage responseMessage = ResponseMessage
                .withResponseData(userRequestDto,
                        "user find successful",
                        "message");


        return new ResponseEntity<ResponseMessage<?>>(responseMessage, HttpStatus.ACCEPTED);
    }


    @GetMapping("/refresh")
    public RefreshResponse newAccessTokenFromRefreshToken(@CookieValue("refresh_token") String refreshToken){
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

