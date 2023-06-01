package com.example.carsharingservice.controller;

import com.example.carsharingservice.dto.request.UserLoginRequestDto;
import com.example.carsharingservice.dto.request.UserRegistrationRequestDto;
import com.example.carsharingservice.dto.response.UserLoginResponseDto;
import com.example.carsharingservice.model.User;
import com.example.carsharingservice.security.AuthenticationService;
import com.example.carsharingservice.security.jwt.JwtTokenProvider;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthenticationController(AuthenticationService authenticationService,
                                    JwtTokenProvider jwtTokenProvider) {
        this.authenticationService = authenticationService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/register")
    public void registerNewUser(
            @RequestBody UserRegistrationRequestDto userRegistrationRequestDto
    ) {
        authenticationService.register(
                userRegistrationRequestDto.getEmail(),
                userRegistrationRequestDto.getPassword(),
                userRegistrationRequestDto.getFirstName(),
                userRegistrationRequestDto.getLastName(),
                userRegistrationRequestDto.getTelegramId()
        );
    }

    @PostMapping("/login")
    public UserLoginResponseDto login(@RequestBody UserLoginRequestDto userLoginRequestDto) {
        User user = authenticationService.login(userLoginRequestDto.getEmail(),
                userLoginRequestDto.getPassword());
        String token = jwtTokenProvider.createToken(user.getEmail(), user.getRole());
        UserLoginResponseDto userLoginResponseDto = new UserLoginResponseDto();
        userLoginResponseDto.setToken(token);
        return userLoginResponseDto;
    }
}
