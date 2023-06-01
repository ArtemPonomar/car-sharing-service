package com.example.carsharingservice.service;

import java.math.BigDecimal;
import com.example.carsharingservice.model.Payment;

public interface PaymentHandler {
    BigDecimal calculateTotalAmount(BigDecimal dailyFee, BigDecimal overdueDays);

    boolean isApplicable(Payment.Type type);
}
