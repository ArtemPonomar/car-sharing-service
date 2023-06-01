package com.example.carsharingservice.service.impl;

import java.math.BigDecimal;
import com.example.carsharingservice.model.Payment;
import com.example.carsharingservice.service.PaymentHandler;
import org.springframework.stereotype.Service;

@Service
public class FinePaymentHandler implements PaymentHandler {
    private static final BigDecimal FINE_MULTIPLIER = BigDecimal.valueOf(1.2);
    @Override
    public BigDecimal calculateTotalAmount(BigDecimal dailyFee, BigDecimal overdueDays) {
        return  dailyFee.multiply(FINE_MULTIPLIER).multiply(overdueDays);
    }

    @Override
    public boolean isApplicable(Payment.Type type) {
        return type.equals(Payment.Type.FINE);
    }
}
