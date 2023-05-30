package com.example.carsharingservice.controller;

import com.example.carsharingservice.dto.mapper.impl.RentalMapper;
import com.example.carsharingservice.dto.request.RentalRequestDto;
import com.example.carsharingservice.dto.response.RentalResponseDto;
import com.example.carsharingservice.exception.NoCarsAvailableException;
import com.example.carsharingservice.model.Car;
import com.example.carsharingservice.model.Rental;
import com.example.carsharingservice.service.CarService;
import com.example.carsharingservice.service.RentalService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rental")
@RequiredArgsConstructor
public class RentalController {
    private final RentalService rentalService;
    private final RentalMapper rentalMapper;
    private final CarService carService;

    @PostMapping
    public RentalResponseDto add(@RequestBody RentalRequestDto requestDto) {
        Rental rental = rentalMapper.toModel(requestDto);
        final Rental savedRental = rentalService.add(rental);
        final Car car = rental.getCar();
        if (car.getInventory() == 0) {
            throw new NoCarsAvailableException("This car is currently unavailable for rental.");
        }
        car.setInventory(car.getInventory() - 1);
        carService.update(car);
        return rentalMapper.toDto(savedRental);
    }

    @GetMapping("/{id}")
    public RentalResponseDto getById(@PathVariable Long id) {
        Rental rental = rentalService.getById(id);
        return rentalMapper.toDto(rental);
    }

    @PostMapping("/{id}/return")
    public RentalResponseDto returnRental(@PathVariable Long id) {
        Rental rental = rentalService.returnRental(id);
        Car car = rental.getCar();
        car.setInventory(car.getInventory() + 1);
        carService.update(car);
        return rentalMapper.toDto(rental);
    }

    @GetMapping
    public List<RentalResponseDto> getByUserIdAndActive(
            @RequestParam("user_id") Long userId,
            @RequestParam("is_active") Boolean isActive) {
        List<Rental> rentals = rentalService.getByUserIdAndActive(userId, isActive);
        return rentals.stream().map(rentalMapper::toDto).collect(Collectors.toList());
    }

}
