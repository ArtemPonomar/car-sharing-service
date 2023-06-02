package com.example.carsharingservice.service.impl;

import com.example.carsharingservice.model.Car;
import com.example.carsharingservice.model.Payment;
import com.example.carsharingservice.model.Rental;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import static com.example.carsharingservice.model.Car.CarType.SEDAN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FinePaymentHandlerTest {

    private static final BigDecimal FINE_MULTIPLIER = BigDecimal.valueOf(1.4);
    private FinePaymentHandler finePaymentHandler;

    @BeforeEach
    void setUp() {
        finePaymentHandler = new FinePaymentHandler();
    }

    @Test
    void calculateTotalAmount() {
        Car car = new Car();
        car.setId(1L);
        car.setCarType(SEDAN);
        car.setDailyFee(new BigDecimal("300"));
        car.setBrand("Toyota");
        car.setModel("Camry");

        Rental rental = new Rental();
        rental.setCar(car);
        rental.setRentalDate(LocalDateTime.now().minusDays(10)); // rented 10 days ago
        rental.setReturnDate(LocalDateTime.now().minusDays(2)); // should have returned 2 days ago
        rental.setActualReturnDate(LocalDateTime.now()); // returned today

        BigDecimal rentalDays = new BigDecimal(ChronoUnit.DAYS.between(rental.getRentalDate(), rental.getReturnDate()));
        BigDecimal overdueDays = new BigDecimal(ChronoUnit.DAYS.between(rental.getReturnDate(), rental.getActualReturnDate()));

        BigDecimal expectedPayment = rentalDays.multiply(car.getDailyFee()); // 10 days of rent
        BigDecimal expectedFine = overdueDays.multiply(car.getDailyFee()).multiply(FINE_MULTIPLIER); // 2 days of delay

        BigDecimal totalAmount = finePaymentHandler.calculateTotalAmount(rental);

        assertEquals(expectedPayment.add(expectedFine), totalAmount);
    }
    @Test
    void isApplicable_FineType_ReturnsTrue() {
        assertTrue(finePaymentHandler.isApplicable(Payment.Type.FINE));
    }

    @Test
    void isApplicable_NotFineType_ReturnsFalse() {
        for (Payment.Type type : Payment.Type.values()) {
            if (!type.equals(Payment.Type.FINE)) {
                assertFalse(finePaymentHandler.isApplicable(type));
            }
        }
    }
}