package com.example.carsharingservice.service.impl;

import java.math.BigDecimal;
import java.util.Optional;
import com.example.carsharingservice.model.Car;
import com.example.carsharingservice.repository.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static com.example.carsharingservice.model.Car.CarType.SEDAN;
import static com.example.carsharingservice.model.Car.CarType.SUV;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class CarServiceImplTest {
    private CarRepository carRepository;
    private CarServiceImpl carService;

    @BeforeEach
    void setUp() {
        carRepository = Mockito.mock(CarRepository.class);
        carService = new CarServiceImpl(carRepository);
    }

    @Test
    void update() {
        Car existingCar = new Car();
        existingCar.setId(1L);
        existingCar.setCarType(SEDAN);
        existingCar.setDailyFee(new BigDecimal("300"));
        existingCar.setBrand("Toyota");
        existingCar.setModel("Camry");

        Car updatedCar = new Car();
        updatedCar.setId(1L);
        updatedCar.setCarType(SUV);
        updatedCar.setDailyFee(new BigDecimal("400"));
        updatedCar.setBrand("Toyota");
        updatedCar.setModel("Land Cruiser");

        when(carRepository.findById(anyLong())).thenReturn(Optional.of(existingCar));
        when(carRepository.save(any(Car.class))).thenReturn(updatedCar);

        carService.update(updatedCar);

        verify(carRepository, times(1)).findById(anyLong());
        verify(carRepository, times(1)).save(any(Car.class));
    }
}