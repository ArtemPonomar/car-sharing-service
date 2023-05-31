package com.example.carsharingservice.repository;

import com.example.carsharingservice.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.net.URL;


@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Payment findByRentalId(Long rental_id);

    Payment updatePaymentById(Long id);
}
