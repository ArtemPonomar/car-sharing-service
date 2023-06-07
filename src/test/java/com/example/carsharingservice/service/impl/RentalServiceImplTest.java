package com.example.carsharingservice.service.impl;

import com.example.carsharingservice.model.Car;
import com.example.carsharingservice.model.Rental;
import com.example.carsharingservice.repository.RentalRepository;
import com.example.carsharingservice.service.MessagingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RentalServiceImplTest {

    @Mock
    private RentalRepository rentalRepository;

    @Mock
    private MessagingService messagingService;

    private RentalServiceImpl rentalService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        rentalService = new RentalServiceImpl(rentalRepository, messagingService);
    }

    @Test
    void returnRental() {
        Rental rental = new Rental();
        rental.setId(1L);

        when(rentalRepository.findById(1L)).thenReturn(Optional.of(rental));
        when(rentalRepository.save(rental)).thenReturn(rental);

        Rental returnedRental = rentalService.returnRental(1L);

        assertNotNull(returnedRental.getActualReturnDate());
        verify(rentalRepository).save(returnedRental);
    }

    @Test
    void getByUserIdAndActive() {
        List<Rental> rentals = new ArrayList<>();
        Rental rental = new Rental();
        rental.setId(1L);
        rental.setActualReturnDate(null);
        rentals.add(rental);

        when(rentalRepository.findAll()).thenReturn(rentals);

        List<Rental> activeRentals = rentalService.getByUserIdAndActive(1L, true);

        assertFalse(activeRentals.isEmpty());
        assertNull(activeRentals.get(0).getActualReturnDate());
    }

    @Test
    void checkOverdueRentals() {
        List<Rental> rentals = new ArrayList<>();
        Rental rental = new Rental();
        rental.setId(1L);
        rental.setReturnDate(LocalDateTime.now().minusDays(1));

        Car car = new Car();
        car.setBrand("Test Brand");
        rental.setCar(car);

        rentals.add(rental);

        when(rentalRepository.findOverdueRentals()).thenReturn(rentals);

        rentalService.checkOverdueRentals();

        verify(messagingService).sendMessageToUser(anyString(), any());
    }

}
