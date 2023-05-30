package com.example.carsharingservice.controller;

import com.example.carsharingservice.dto.exception.NoCarsAvailableExceptionDto;
import com.example.carsharingservice.exception.NoCarsAvailableException;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CarSharingGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    public static final String NO_CARS_AVAILABLE_EXCEPTION_TEXT
            = "This car is currently unavailable for rental.";

    @ExceptionHandler(NoCarsAvailableException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<NoCarsAvailableExceptionDto> handleNoCarsAvailableException(
            NoCarsAvailableException ex
    ) {
        NoCarsAvailableExceptionDto noCarsAvailableExceptionDto = new NoCarsAvailableExceptionDto();
        noCarsAvailableExceptionDto.setMessage(NO_CARS_AVAILABLE_EXCEPTION_TEXT);
        noCarsAvailableExceptionDto.setTimestamp(LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(noCarsAvailableExceptionDto);
    }
}
