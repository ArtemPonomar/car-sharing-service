package com.example.carsharingservice.dto.exception;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class NoCarsAvailableExceptionDto {
    private String message;
    private LocalDateTime timestamp;
}
