package com.example.carsharingservice.service;

import com.example.carsharingservice.model.Rental;

public interface RentalService {
    Rental add(Rental rental);

    Rental getById(Long id);

    void update(Rental rental);

    Rental returnRental(Long id);
}
