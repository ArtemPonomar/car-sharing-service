package com.example.carsharingservice.service.impl;

import com.example.carsharingservice.model.Payment;
import com.example.carsharingservice.model.Rental;
import com.example.carsharingservice.service.PaymentHandler;
import java.math.BigDecimal;
import java.time.Duration;
import org.springframework.stereotype.Service;

@Service
public class StandartPaymentHandler implements PaymentHandler {
    @Override
    public BigDecimal calculateTotalAmount(Rental rental) {
        BigDecimal days = BigDecimal.valueOf(Duration.between(
                rental.getRentalDate(), rental.getReturnDate()).toDays());
        return days.multiply(rental.getCar().getDailyFee());
    }

    @Override
    public boolean isApplicable(Payment.Type type) {
        return type.equals(Payment.Type.PAYMENT);
    }
}
