package com.example.carsharingservice.service;

import java.math.BigDecimal;
import com.example.carsharingservice.model.Payment;

public interface PaymentHandler {
    BigDecimal calculateTotalAmount(BigDecimal amount, long overdueDays);

    boolean isApplecable(Payment.Type type);
}
