package com.example.carsharingservice.service;

import com.example.carsharingservice.model.Payment;
import java.math.BigDecimal;
import java.util.List;

public interface PaymentService {
    Payment save(Payment payment);

    Payment findById(Long id);

    List<Payment> findAll();

    Payment update(Payment payment);

    void delete(Long id);

    public List<Payment> getPaymentsByUserId(Long userId);

    BigDecimal calculatePaymentAmount(Long rentalId, Payment.Type type);
}
