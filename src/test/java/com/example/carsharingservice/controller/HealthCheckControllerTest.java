package com.example.carsharingservice.controller;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
class HealthCheckControllerTest {
    @Test
    void healthCheck() {
        // Arrange
        HealthCheckController controller = new HealthCheckController();
        // Act
        ResponseEntity<String> response = controller.healthCheck();
        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Application is running", response.getBody());
    }
}