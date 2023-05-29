package com.example.carsharingservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.net.URL;
import lombok.Data;

@Data
@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int sessionId;
    int rentalId;
    URL url;
    BigDecimal paymentAmount;
    Type type;
    Status status;
}

enum Status {
    PENDING,
    PAID
}

enum Type {
    PAYMENT,
    FINE
}
