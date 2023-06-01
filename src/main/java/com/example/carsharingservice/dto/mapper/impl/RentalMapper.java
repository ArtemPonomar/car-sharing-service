package com.example.carsharingservice.dto.mapper.impl;

import com.example.carsharingservice.dto.mapper.DtoMapper;
import com.example.carsharingservice.dto.request.RentalRequestDto;
import com.example.carsharingservice.dto.response.RentalResponseDto;
import com.example.carsharingservice.model.Rental;
import com.example.carsharingservice.service.CarService;
import com.example.carsharingservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RentalMapper implements DtoMapper<Rental, RentalRequestDto, RentalResponseDto> {
    private final CarService carService;
    private final UserService userService;

    @Autowired
    public RentalMapper(CarService carService, UserService userService) {
        this.carService = carService;
        this.userService = userService;
    }

    @Override
    public Rental toModel(RentalRequestDto requestDto) {
        Rental rental = new Rental();
        rental.setRentalDate(requestDto.getRentalDate());
        rental.setReturnDate(requestDto.getReturnDate());
        rental.setActualReturnDate(requestDto.getActualReturnDate());
        rental.setCar(carService.getById(requestDto.getCarId()));
        rental.setUser(userService.getById(requestDto.getUserId()));
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
