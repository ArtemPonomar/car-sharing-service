package com.example.carsharingservice.repository;

import com.example.carsharingservice.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    @Query("SELECT p FROM Payment p "
            + "JOIN FETCH p.rental r "
            + "JOIN FETCH r.user "
            + "WHERE p.id = (:id)")
    Payment findByRentalId(Long id);

    @Query("SELECT p FROM Payment p "
            + "JOIN FETCH p.rental r "
            + "JOIN FETCH r.user u "
            + "JOIN FETCH r.car c "
            + "WHERE p.sessionId = (:sessionId)")
    Payment findBySessionId(String sessionId);
}

