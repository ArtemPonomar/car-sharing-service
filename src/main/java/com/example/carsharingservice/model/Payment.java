package com.example.carsharingservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.net.URL;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Getter
@Setter
@Entity
@Table(name = "payments")
@SQLDelete(sql = "UPDATE payments SET deleted = true WHERE id=?")
@Where(clause = "deleted=false")
public class Payment {
    @Id
    @GeneratedValue()
    private Long rentalId;
    private String sessionId;
    private URL url;
    private BigDecimal paymentAmount;
    @Enumerated(value = EnumType.STRING)
    private Type type;
    @Enumerated(value = EnumType.STRING)
    private Status status;
    private boolean deleted = Boolean.FALSE;

    public enum Status {
        PENDING,
        PAID
    }

    public enum Type {
        PAYMENT,
        FINE
    }
}
