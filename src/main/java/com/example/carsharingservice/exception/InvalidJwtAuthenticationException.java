package com.example.carsharingservice.exception;

public class InvalidJwtAuthenticationException extends RuntimeException {
    public InvalidJwtAuthenticationException(String expiredOrInvalidJwtToken) {
        super(expiredOrInvalidJwtToken);
    }

    public InvalidJwtAuthenticationException() {
    }
}
