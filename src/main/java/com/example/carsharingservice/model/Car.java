package com.example.carsharingservice.model;

import java.math.BigDecimal;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "cars")
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String model;
    private String brand;
    private Integer inventory;
    @Enumerated(value = EnumType.STRING)
    private CarType carType;
    private BigDecimal dailyFee;

    enum CarType {
        SEDAN,
        SUV,
        HATCHBACK,
        UNIVERSAL
    }
}

