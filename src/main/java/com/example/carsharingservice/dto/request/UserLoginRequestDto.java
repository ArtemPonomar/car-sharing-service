package com.example.carsharingservice.dto.request;

import lombok.Data;

@Data
public class UserLoginRequestDto {
    private String email;
    private String password;
}
