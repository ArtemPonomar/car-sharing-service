package com.example.carsharingservice.service.impl;

import com.example.carsharingservice.model.Payment;
import com.example.carsharingservice.model.Rental;
import com.example.carsharingservice.repository.PaymentRepository;
import com.example.carsharingservice.service.PaymentHandler;
import com.example.carsharingservice.service.PaymentHandlerStrategy;
import com.example.carsharingservice.service.RentalService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentServiceImplTest {
    private static PaymentServiceImpl paymentService;
    private static PaymentRepository paymentRepository;
    private static RentalService rentalService;
    private static PaymentHandlerStrategy strategy;

    @BeforeAll
    static void beforeAll() {
        paymentRepository = Mockito.mock(PaymentRepository.class);
        rentalService = Mockito.mock(RentalService.class);
        strategy = Mockito.mock(PaymentHandlerStrategy.class);
        paymentService = new PaymentServiceImpl(paymentRepository, rentalService, strategy);
    }

    @Test
    void getPaymentsByUserId() {
        Rental rental = mock(Rental.class);
        when(rental.getId()).thenReturn(1L);

        when(rentalService.getByUserIdAndActive(1L, false)).thenReturn(Collections.singletonList(rental));

        Payment payment = mock(Payment.class);
        when(paymentRepository.findByRentalId(1L)).thenReturn(payment);

        assertEquals(Collections.singletonList(payment), paymentService.getPaymentsByUserId(1L));
    }

    @Test
    void calculatePaymentAmount() {
        Rental rental = mock(Rental.class);
        when(rentalService.getById(1L)).thenReturn(rental);

        // Create a mock PaymentHandler
        PaymentHandler mockHandler = mock(PaymentHandler.class);
        when(mockHandler.calculateTotalAmount(rental)).thenReturn(BigDecimal.TEN);

        // Mock the return of getHandler() to return the mock handler
        when(strategy.getHandler(Payment.Type.FINE)).thenReturn(mockHandler);

        assertEquals(BigDecimal.valueOf(1000), paymentService.calculatePaymentAmount(1L, Payment.Type.FINE));
    }


    @Test
    void isSessionPaid() {
        Payment payment = mock(Payment.class);
        when(payment.getStatus()).thenReturn(Payment.Status.PAID);

        when(paymentRepository.findBySessionId("test")).thenReturn(payment);

        assertTrue(paymentService.isSessionPaid("test"));
    }
}
