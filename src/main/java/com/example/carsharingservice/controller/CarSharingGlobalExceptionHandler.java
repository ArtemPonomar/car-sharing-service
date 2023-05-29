package com.example.carsharingservice.controller;

import com.example.carsharingservice.dto.exception.NoCarsAvailableExceptionDto;
import com.example.carsharingservice.exception.NoCarsAvailableException;
import java.time.LocalDateTime;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class CarSharingGlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(NoCarsAvailableException.class)
    public NoCarsAvailableExceptionDto handleNoCarsAvailableException(
            NoCarsAvailableException ex
    ) {
        NoCarsAvailableExceptionDto noCarsAvailableExceptionDto = new NoCarsAvailableExceptionDto();
        noCarsAvailableExceptionDto.setMessage(ex.getMessage());
        noCarsAvailableExceptionDto.setTimestamp(LocalDateTime.now());
        return noCarsAvailableExceptionDto;
    }
}
