package com.example.carsharingservice.controller;

import com.example.carsharingservice.dto.mapper.DtoMapper;
import com.example.carsharingservice.dto.request.CarRequestDto;
import com.example.carsharingservice.dto.response.CarResponseDto;
import com.example.carsharingservice.model.Car;
import com.example.carsharingservice.service.CarService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cars")
@AllArgsConstructor
public class CarController {
    private final CarService carService;
    private final DtoMapper<Car, CarRequestDto, CarResponseDto> mapper;

    @PostMapping
    @Operation(summary = "Create Car and add to DB")
    public CarResponseDto add(@Schema(description = "Create new car",
            implementation = CarRequestDto.class,
            requiredMode = Schema.RequiredMode.REQUIRED)
            @RequestBody CarRequestDto requestDto) {
        return mapper.toDto(carService.save(mapper.toModel(requestDto)));
    }

    @GetMapping
    @Operation(summary = "Get list of all cars")
    public List<CarResponseDto> getAllCars() {
        return carService.getAll().stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get car by id")
    public CarResponseDto getCarById(@PathVariable Long id) {
        return mapper.toDto(carService.getById(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update product by id")
    public void updateById(@PathVariable Long id,
                                     @Schema(description = "Update car",
                                     requiredMode = Schema.RequiredMode.REQUIRED,
                                     implementation = CarRequestDto.class)
                                     @RequestBody CarRequestDto requestDto) {
        carService.update(mapper.toModel(requestDto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete car by id")
    public void deleteById(@PathVariable Long id) {
        carService.delete(id);
    }
}
