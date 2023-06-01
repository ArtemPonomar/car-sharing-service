package com.example.carsharingservice.service.impl;

import java.math.BigDecimal;
import java.time.Duration;
import com.example.carsharingservice.model.Payment;
import com.example.carsharingservice.model.Rental;
import com.example.carsharingservice.service.PaymentHandler;
import org.springframework.stereotype.Service;

@Service
public class FinePaymentHandler implements PaymentHandler {
    private static final BigDecimal FINE_MULTIPLIER = BigDecimal.valueOf(1.4);
    @Override
    public BigDecimal calculateTotalAmount(Rental rental) {
        BigDecimal days = BigDecimal.valueOf(Duration.between(
                rental.getRentalDate(), rental.getReturnDate()).toDays());
        BigDecimal overdueDays = BigDecimal.valueOf(
                Duration.between(rental.getReturnDate(),
                        rental.getActualReturnDate()).toDays());
        BigDecimal rentalPayment = days.multiply(rental.getCar().getDailyFee());
        return  rental.getCar().getDailyFee().multiply(FINE_MULTIPLIER).multiply(overdueDays)
                .add(rentalPayment);
    }

    @Override
    public boolean isApplicable(Payment.Type type) {
        return type.equals(Payment.Type.FINE);
    }
}
