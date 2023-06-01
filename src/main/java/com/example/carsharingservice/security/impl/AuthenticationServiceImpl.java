package com.example.carsharingservice.security.impl;

import com.example.carsharingservice.exception.AuthenticationException;
import com.example.carsharingservice.model.User;
import com.example.carsharingservice.security.AuthenticationService;
import com.example.carsharingservice.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationServiceImpl(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User register(String email,
                         String password,
                         String firstName,
                         String lastName,
                         Long telegramId) {
        User user = new User();
        user.setTelegramId(telegramId);
        user.setRole(User.Role.CUSTOMER);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        user.setFirstName(firstName);
        user.setLastName(lastName);
        userService.add(user);
        return user;
    }

    @Override
    public User login(String email, String password) throws AuthenticationException {
        User user = userService.getByEmail(email);
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new AuthenticationException("Incorrect username or password");
        }
        return user;
    }
}
