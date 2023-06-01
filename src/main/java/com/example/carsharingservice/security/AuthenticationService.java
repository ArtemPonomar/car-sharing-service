package com.example.carsharingservice.security;

import com.example.carsharingservice.exception.AuthenticationException;
import com.example.carsharingservice.model.User;

public interface AuthenticationService {
    User register(String email,
                  String password,
                  String firstName,
                  String lastName,
                  Long telegramId);

    User login(String email, String password) throws AuthenticationException;
}
