package com.example.carsharingservice.service;

import java.util.List;
import com.example.carsharingservice.model.Rental;

public interface RentalService {
    Rental add(Rental rental);

    Rental getById(Long id);

    Rental returnRental(Long id);

    List<Rental> getByUserIdAndActive(Long userId, Boolean isActive);
}
