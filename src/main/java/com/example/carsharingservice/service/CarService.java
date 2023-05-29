package com.example.carsharingservice.service;

import com.example.carsharingservice.model.Car;

public interface CarService {
    Car add(Car car);

    Car getById(Long id);

    void delete(Long id);

    void update(Car car);
}
