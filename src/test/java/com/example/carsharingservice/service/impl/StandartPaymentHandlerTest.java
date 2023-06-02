package com.example.carsharingservice.service.impl;

import com.example.carsharingservice.model.Car;
import com.example.carsharingservice.model.Payment;
import com.example.carsharingservice.model.Rental;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class StandartPaymentHandlerTest {

    @Test
    void calculateTotalAmount() {
        StandartPaymentHandler handler = new StandartPaymentHandler();

        Car car = new Car();
        car.setDailyFee(BigDecimal.valueOf(100));

        Rental rental = new Rental();
        rental.setCar(car);
        rental.setRentalDate(LocalDateTime.now().minusDays(3));
        rental.setReturnDate(LocalDateTime.now());

        BigDecimal total = handler.calculateTotalAmount(rental);

        assertEquals(BigDecimal.valueOf(300), total);
    }

    @Test
    void isApplicable() {
        StandartPaymentHandler handler = new StandartPaymentHandler();

        assertTrue(handler.isApplicable(Payment.Type.PAYMENT));
        assertFalse(handler.isApplicable(Payment.Type.FINE));
    }
}
