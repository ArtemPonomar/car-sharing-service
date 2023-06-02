package com.example.carsharingservice.service;

import com.example.carsharingservice.model.Rental;
import java.util.List;

public interface RentalService {
    Rental add(Rental rental);

    Rental getById(Long id);

    Rental returnRental(Long id);

    List<Rental> getByUserIdAndActive(Long userId, Boolean isActive);
}
