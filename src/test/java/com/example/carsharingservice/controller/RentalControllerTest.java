package com.example.carsharingservice.controller;
import com.example.carsharingservice.dto.mapper.impl.RentalMapper;
import com.example.carsharingservice.dto.request.RentalRequestDto;
import com.example.carsharingservice.dto.response.RentalResponseDto;
import com.example.carsharingservice.model.Car;
import com.example.carsharingservice.model.Rental;
import com.example.carsharingservice.service.CarService;
import com.example.carsharingservice.service.RentalService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
@ExtendWith(MockitoExtension.class)
class RentalControllerTest {
    @Mock
    private RentalService rentalService;
    @Mock
    private RentalMapper rentalMapper;
    @Mock
    private CarService carService;
    @InjectMocks
    private RentalController controller;
    @BeforeEach
    void setUp() {
        // Initialize common mock interactions
    }
    @Test
    void getById() {
        Long id = 1L;
        Rental rental = new Rental();
        RentalResponseDto responseDto = new RentalResponseDto();
        when(rentalService.getById(id)).thenReturn(rental);
        when(rentalMapper.toDto(rental)).thenReturn(responseDto);
        RentalResponseDto result = controller.getById(id);
        assertEquals(responseDto, result);
    }
    @Test
    void add() {
        RentalRequestDto requestDto = new RentalRequestDto();
        Rental rental = new Rental();
        Car car = new Car();
        car.setInventory(1);
        rental.setCar(car);
        Rental savedRental = new Rental();
        RentalResponseDto responseDto = new RentalResponseDto();
        when(rentalMapper.toModel(requestDto)).thenReturn(rental);
        when(rentalService.add(rental)).thenReturn(savedRental);
        doNothing().when(carService).update(car);
        when(rentalMapper.toDto(savedRental)).thenReturn(responseDto);
        RentalResponseDto result = controller.add(requestDto);
        verify(carService).update(car); // verify that update was called
        assertEquals(responseDto, result);
    }
    @Test
    void returnRental() {
        Long id = 1L;
        Rental rental = new Rental();
        Car car = new Car();
        car.setInventory(0);
        rental.setCar(car); // rental now has a car associated with it
        Rental returnedRental = new Rental();
        returnedRental.setCar(car); // returnedRental also has a car associated with it
        RentalResponseDto responseDto = new RentalResponseDto();
        when(rentalService.returnRental(id)).thenReturn(returnedRental); // when returnRental is called, it returns a rental that has a car
        doNothing().when(carService).update(car);
        when(rentalMapper.toDto(returnedRental)).thenReturn(responseDto);
        RentalResponseDto result = controller.returnRental(id);
        verify(carService).update(car); // verify that update was called
        assertEquals(responseDto, result);
    }
    @Test
    void getByUserIdAndActive() {
        Long userId = 1L;
        Boolean isActive = true;
        List<Rental> rentals = Arrays.asList(new Rental(), new Rental());
        List<RentalResponseDto> responseDtos = Arrays.asList(new RentalResponseDto(), new RentalResponseDto());
        when(rentalService.getByUserIdAndActive(userId, isActive)).thenReturn(rentals);
        when(rentalMapper.toDto(any(Rental.class))).thenReturn(responseDtos.get(0), responseDtos.get(1));
        List<RentalResponseDto> result = controller.getByUserIdAndActive(userId, isActive);
        assertEquals(responseDtos, result);
    }
}