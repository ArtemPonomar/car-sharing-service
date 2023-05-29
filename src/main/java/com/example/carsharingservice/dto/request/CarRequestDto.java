package com.example.carsharingservice.dto.request;

import com.example.carsharingservice.model.Car;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class CarRequestDto {
    private String model;
    private String brand;
    private Integer inventory;
    private Car.CarType carType;
    private BigDecimal dailyFee;
}
