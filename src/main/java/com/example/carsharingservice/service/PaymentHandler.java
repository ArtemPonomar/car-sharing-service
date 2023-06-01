package com.example.carsharingservice.service;

import com.example.carsharingservice.model.Payment;
import com.example.carsharingservice.model.Rental;
import java.math.BigDecimal;

public interface PaymentHandler {
    BigDecimal calculateTotalAmount(Rental rental);

    boolean isApplicable(Payment.Type type);
}
