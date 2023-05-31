package com.example.carsharingservice.service.impl;

import java.math.BigDecimal;
import com.example.carsharingservice.model.Payment;
import com.example.carsharingservice.service.PaymentHandler;
import org.springframework.stereotype.Service;

@Service
public class FinePaymentHandler implements PaymentHandler {
    private static final BigDecimal FINE_MULTIPLIER = BigDecimal.valueOf(1);
    @Override
    public BigDecimal calculateTotalAmount(BigDecimal amount, long overdueDays) {
        return  amount.multiply(FINE_MULTIPLIER);
    }

    @Override
    public boolean isApplecable(Payment.Type type) {
        return type.equals(Payment.Type.FINE);
    }
}
