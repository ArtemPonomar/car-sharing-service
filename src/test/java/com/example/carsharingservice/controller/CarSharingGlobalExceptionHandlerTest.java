package com.example.carsharingservice.controller;
import com.example.carsharingservice.dto.exception.NoCarsAvailableExceptionDto;
import com.example.carsharingservice.exception.NoCarsAvailableException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
class CarSharingGlobalExceptionHandlerTest {
    @InjectMocks
    private CarSharingGlobalExceptionHandler carSharingGlobalExceptionHandler;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void handleNoCarsAvailableException() {
        NoCarsAvailableException ex = new NoCarsAvailableException();
        LocalDateTime beforeHandling = LocalDateTime.now();
        ResponseEntity<NoCarsAvailableExceptionDto> responseEntity =
                carSharingGlobalExceptionHandler.handleNoCarsAvailableException(ex);
        LocalDateTime afterHandling = LocalDateTime.now();
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        NoCarsAvailableExceptionDto noCarsAvailableExceptionDto = responseEntity.getBody();
        assertEquals(CarSharingGlobalExceptionHandler.NO_CARS_AVAILABLE_EXCEPTION_TEXT, noCarsAvailableExceptionDto.getMessage());
        assertTrue(noCarsAvailableExceptionDto.getTimestamp().isAfter(beforeHandling) || noCarsAvailableExceptionDto.getTimestamp().isEqual(beforeHandling));
        assertTrue(noCarsAvailableExceptionDto.getTimestamp().isBefore(afterHandling) || noCarsAvailableExceptionDto.getTimestamp().isEqual(afterHandling));
    }
}