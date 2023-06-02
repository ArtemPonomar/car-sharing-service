package com.example.carsharingservice.controller;
import com.example.carsharingservice.dto.mapper.DtoMapper;
import com.example.carsharingservice.dto.request.CarRequestDto;
import com.example.carsharingservice.dto.response.CarResponseDto;
import com.example.carsharingservice.model.Car;
import com.example.carsharingservice.service.CarService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
class CarControllerTest {
    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();
    @InjectMocks
    private CarController carController;
    @Mock
    private CarService carService;
    @Mock
    private DtoMapper<Car, CarRequestDto, CarResponseDto> mapper;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(carController).build();
    }
    @Test
    void add() throws Exception {
        CarRequestDto carRequestDto = new CarRequestDto();
        // Initialize carRequestDto fields...
        Car car = new Car();
        // Initialize car fields...
        CarResponseDto carResponseDto = new CarResponseDto();
        // Initialize carResponseDto fields...
        when(mapper.toModel(carRequestDto)).thenReturn(car);
        when(carService.save(any(Car.class))).thenReturn(car);
        when(mapper.toDto(car)).thenReturn(carResponseDto);
        String response = mockMvc.perform(post("/cars")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carRequestDto)))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        CarResponseDto responseDto = objectMapper.readValue(response, CarResponseDto.class);
        assertEquals(carResponseDto, responseDto);
    }

    @Test
    void getAllCars() throws Exception {
        Car car = new Car();
        // Initialize car fields...
        CarResponseDto carResponseDto = new CarResponseDto();
        // Initialize carResponseDto fields...
        when(carService.getAll()).thenReturn(Collections.singletonList(car));
        when(mapper.toDto(car)).thenReturn(carResponseDto);
        String response = mockMvc.perform(get("/cars")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        List<CarResponseDto> responseDtoList = objectMapper.readValue(response, new TypeReference<List<CarResponseDto>>() {});
        assertEquals(1, responseDtoList.size());
        assertEquals(carResponseDto, responseDtoList.get(0));
    }
    @Test
    void getCarById() throws Exception {
        Car car = new Car();
        // Initialize car fields...
        CarResponseDto carResponseDto = new CarResponseDto();
        // Initialize carResponseDto fields...
        when(carService.getById(1L)).thenReturn(car);
        when(mapper.toDto(car)).thenReturn(carResponseDto);
        String response = mockMvc.perform(get("/cars/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        CarResponseDto responseDto = objectMapper.readValue(response, CarResponseDto.class);
        assertEquals(carResponseDto, responseDto);
    }
    @Test
    void updateById() throws Exception {
        CarRequestDto carRequestDto = new CarRequestDto();
        // Initialize carRequestDto fields...
        Car car = new Car();
        // Initialize car fields...
        when(mapper.toModel(carRequestDto)).thenReturn(car);
        doNothing().when(carService).update(any(Car.class));
        mockMvc.perform(put("/cars/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(carRequestDto)))
                .andExpect(status().isOk());
        verify(carService, times(1)).update(any(Car.class));
    }
    @Test
    void deleteById() throws Exception {
        doNothing().when(carService).delete(1L);
        mockMvc.perform(delete("/cars/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(carService, times(1)).delete(1L);
    }
}