package com.example.carsharingservice.dto.response;

import com.example.carsharingservice.model.Car;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class CarResponseDto {
    private Long id;
    private String model;
    private String brand;
    private Integer inventory;
    private Car.CarType carType;
    private BigDecimal dailyFee;
}
