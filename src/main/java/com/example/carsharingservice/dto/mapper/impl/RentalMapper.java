package com.example.carsharingservice.dto.mapper.impl;

import com.example.carsharingservice.dto.mapper.DtoMapper;
import com.example.carsharingservice.dto.request.RentalRequestDto;
import com.example.carsharingservice.dto.response.RentalResponseDto;
import com.example.carsharingservice.model.Rental;
import com.example.carsharingservice.repository.CarRepository;
import com.example.carsharingservice.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RentalMapper implements DtoMapper<Rental, RentalRequestDto, RentalResponseDto> {
    private final CarRepository carRepository;
    private final UserRepository userRepository;

    @Autowired
    public RentalMapper(CarRepository carRepository, UserRepository userRepository) {
        this.carRepository = carRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Rental toModel(RentalRequestDto requestDto) {
        Rental rental = new Rental();
        rental.setRentalDate(requestDto.getRentalDate());
        rental.setReturnDate(requestDto.getReturnDate());
        rental.setCar(carRepository.findById(requestDto.getCarId()).orElse(null));
        rental.setUser(userRepository.findById(requestDto.getUserId()).orElse(null));
        return rental;
    }

    @Override
    public RentalResponseDto toDto(Rental model) {
        RentalResponseDto responseDto = new RentalResponseDto();
        responseDto.setId(model.getId());
        responseDto.setRentalDate(model.getRentalDate());
        responseDto.setReturnDate(model.getReturnDate());
        responseDto.setActualReturnDate(model.getActualReturnDate());
        responseDto.setCarId(model.getCar().getId());
        responseDto.setUserId(model.getUser().getId());
        return responseDto;
    }
}
