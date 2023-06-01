package com.example.carsharingservice.repository;

import com.example.carsharingservice.model.Rental;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {

    @Query("SELECT r FROM Rental r "
            + "JOIN FETCH r.car "
            + "JOIN FETCH r.user "
            + "WHERE r.actualReturnDate IS NULL "
            + "AND r.returnDate < (:now)")
    List<Rental> findOverdueRentals(LocalDateTime now);
}
