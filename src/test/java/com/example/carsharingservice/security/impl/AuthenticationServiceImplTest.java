package com.example.carsharingservice.security.impl;

import com.example.carsharingservice.exception.AuthenticationException;
import com.example.carsharingservice.model.User;
import com.example.carsharingservice.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class AuthenticationServiceImplTest {
    @Mock
    UserService userService;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    AuthenticationServiceImpl authenticationService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void register() {
        User user = new User();
        user.setEmail("email@example.com");
        when(userService.add(any(User.class))).thenReturn(user);
        when(passwordEncoder.encode(any(String.class))).thenReturn("encodedPassword");

        User result = authenticationService.register("email@example.com", "password", "firstName", "lastName", 123L);
        assertEquals(user.getEmail(), result.getEmail());
    }

    @Test
    void login() {
        User user = new User();
        user.setEmail("email@example.com");
        user.setPassword("password");
        when(userService.getByEmail(any(String.class))).thenReturn(user);
        when(passwordEncoder.matches(any(String.class), any(String.class))).thenReturn(true);

        User result = authenticationService.login("email@example.com", "password");
        assertEquals(user.getEmail(), result.getEmail());
    }

    @Test
    void login_fail() {
        User user = new User();
        user.setEmail("email@example.com");
        user.setPassword("password");
        when(userService.getByEmail(any(String.class))).thenReturn(user);
        when(passwordEncoder.matches(any(String.class), any(String.class))).thenReturn(false);

        assertThrows(AuthenticationException.class, () -> authenticationService.login("email@example.com", "wrong_password"));
    }
}
