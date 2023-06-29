package com.example.carsharingservice.service.impl;

import com.example.carsharingservice.model.Car;
import com.example.carsharingservice.repository.CarRepository;
import com.example.carsharingservice.service.CarService;
import java.util.List;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CarServiceImpl implements CarService {
    private final CarRepository carRepository;

    @Override
    public Car save(Car car) {
        return carRepository.save(car);
    }

    @Override
    public Car getById(Long id) {
        return carRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Can't find car by id: " + id));
    }

    @Override
    public void delete(Long id) {
        carRepository.deleteById(id);
    }

    @Override
    public void update(Car car) {
        Optional<Car> optionalCar = carRepository.findById(car.getId());
        if (optionalCar.isEmpty()) {
            throw new RuntimeException("Car not found: " + car);
        }
        Car updateCar = optionalCar.get();
        updateCar.setCarType(car.getCarType());
        updateCar.setDailyFee(car.getDailyFee());
        updateCar.setBrand(car.getBrand());
        updateCar.setModel(car.getModel());
        carRepository.save(updateCar);
    }

    @Override
    public List<Car> getAll() {
        return carRepository.findAll();
    }
}
