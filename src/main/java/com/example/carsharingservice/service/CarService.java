package com.example.carsharingservice.service;

import com.example.carsharingservice.model.Car;
import java.util.List;

public interface CarService {
    Car save(Car car);

    Car getById(Long id);

    void delete(Long id);

    void update(Car car);

    List<Car> getAll();
}
