package com.example.carsharingservice.service.impl;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import com.example.carsharingservice.model.Payment;
import com.example.carsharingservice.model.Rental;
import com.example.carsharingservice.repository.PaymentRepository;
import com.example.carsharingservice.service.PaymentHandlerStrategy;
import com.example.carsharingservice.service.PaymentService;
import com.example.carsharingservice.service.RentalService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PaymentServiceImpl implements PaymentService {
    private final PaymentRepository paymentRepository;
    private final RentalService rentalService;
    private final PaymentHandlerStrategy strategy;

    @Override
    public Payment save(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public Payment findById(Long id) {
        return paymentRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("Can't find payment by id: " + id));
    }

    @Override
    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    @Override
    public Payment update(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public void delete(Long id) {
        paymentRepository.deleteById(id);
    }

    @Override
    public List<Payment> getPaymentsByUserId(Long userId) {
        return rentalService.getByUserIdAndActive(userId, false).stream()
                .map(rental -> paymentRepository.findByRentalId(rental.getId()))
                .collect(Collectors.toList());
    }

    @Override
    public BigDecimal calculatePaymentAmount(Long rentalId, Payment.Type type) {
        Rental rental = rentalService.getById(rentalId);
        long days = Duration.between(rental.getReturnDate(), rental.getRentalDate()).toDays();
        long overdueDays = Duration.between(rental.getActualReturnDate(), rental.getReturnDate()).toDays();
        BigDecimal rentalPayment = BigDecimal.valueOf(days).multiply(rental.getCar().getDailyFee());
        return strategy.getHandler(type).calculateTotalAmount(rentalPayment, overdueDays);
    }
}
