package com.example.carsharingservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.net.URL;

@Entity
@Table(name = "payments")
public class Payment {
    @Id
    @GeneratedValue()
    private Long sessionId;
    private Long rentalId;
    private URL url;
    @Enumerated(value = EnumType.STRING)
    private Type type;
    @Enumerated(value = EnumType.STRING)
    private Status status;

    enum Status {
        PENDING,
        PAID
    }

    enum Type {
        PAYMENT,
        FINE
    }
}
