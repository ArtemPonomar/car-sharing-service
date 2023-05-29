package com.example.carsharingservice.dto.mapper.impl;

import com.example.carsharingservice.dto.mapper.DtoMapper;
import com.example.carsharingservice.dto.request.CarRequestDto;
import com.example.carsharingservice.dto.response.CarResponseDto;
import com.example.carsharingservice.model.Car;
import org.springframework.stereotype.Component;

@Component
public class CarMapper implements DtoMapper<Car, CarRequestDto, CarResponseDto> {
    @Override
    public Car toModel(CarRequestDto requestDto) {
        Car car = new Car();
        car.setModel(requestDto.getModel());
        car.setBrand(requestDto.getBrand());
        car.setCarType(requestDto.getCarType());
        car.setInventory(requestDto.getInventory());
        car.setDailyFee(requestDto.getDailyFee());
        return car;
    }

    @Override
    public CarResponseDto toDto(Car model) {
        CarResponseDto responseDto = new CarResponseDto();
        responseDto.setId(model.getId());
        responseDto.setModel(model.getModel());
        responseDto.setBrand(model.getBrand());
        responseDto.setCarType(model.getCarType());
        responseDto.setInventory(model.getInventory());
        responseDto.setDailyFee(model.getDailyFee());
        return responseDto;
    }
}
