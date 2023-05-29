package com.example.carsharingservice.service.impl;

import com.example.carsharingservice.model.Rental;
import com.example.carsharingservice.repository.RentalRepository;
import com.example.carsharingservice.service.RentalService;
import java.time.LocalDateTime;
import org.springframework.stereotype.Service;

@Service
public class RentalServiceImpl implements RentalService {
    private final RentalRepository rentalRepository;

    public RentalServiceImpl(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    @Override
    public Rental add(Rental rental) {
        return rentalRepository.save(rental);
    }

    @Override
    public Rental getById(Long id) {
        return rentalRepository.findById(id).orElseThrow(() ->
                new RuntimeException("Can't find car by id: " + id));
    }

    @Override
    public void update(Rental rental) {
        rentalRepository.save(rental);
    }

    @Override
    public Rental returnRental(Long id) {
        Rental rental = getById(id);
        rental.setActualReturnDate(LocalDateTime.now());
        return rentalRepository.save(rental);
    }
}
