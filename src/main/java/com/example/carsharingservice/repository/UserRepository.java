package com.example.carsharingservice.repository;

import com.example.carsharingservice.model.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
    @Query("SELECT p FROM Payment p "
            + "JOIN FETCH p.rental r "
            + "JOIN FETCH r.user "
            + "WHERE p.id = (:paymentId)")
    User findUserByPaymentId(Long paymentId);
}
