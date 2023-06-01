package com.example.carsharingservice.service;

import java.math.BigDecimal;
import com.example.carsharingservice.model.Payment;
import com.example.carsharingservice.model.Rental;

public interface PaymentHandler {
    BigDecimal calculateTotalAmount(Rental rental);

    boolean isApplicable(Payment.Type type);
}
