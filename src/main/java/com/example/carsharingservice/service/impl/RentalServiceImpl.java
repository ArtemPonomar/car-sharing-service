package com.example.carsharingservice.service.impl;

import java.util.List;
import com.example.carsharingservice.model.Rental;
import com.example.carsharingservice.repository.RentalRepository;
import com.example.carsharingservice.service.RentalService;
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
    public void delete(Long id) {
        rentalRepository.deleteById(id);
    }

    @Override
    public void update(Rental rental) {
        if (rentalRepository.existsById(rental.getId())) {
            rentalRepository.saveAndFlush(rental);
        }
        throw new RuntimeException("This car doesn't exist");
    }

    @Override
    public List<Rental> getAll() {
        return null;
    }
}
