package com.example.carsharingservice.controller;

import com.example.carsharingservice.dto.request.UserRequestDto;
import com.example.carsharingservice.dto.response.UserResponseDto;
import com.example.carsharingservice.model.User;
import com.example.carsharingservice.service.MessagingService;
import com.example.carsharingservice.service.UserService;
import com.example.carsharingservice.dto.mapper.DtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class UserControllerTest {

    @Mock
    private UserService userService;

    @Mock
    private MessagingService messagingService;

    @Mock
    private DtoMapper<User, UserRequestDto, UserResponseDto> mapper;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void updateUserRole() {
        Long id = 1L;
        User.Role role = User.Role.MANAGER;
        User user = new User();
        UserResponseDto userResponseDto = new UserResponseDto();

        when(userService.getById(id)).thenReturn(user);
        when(userService.update(user)).thenReturn(user);
        when(mapper.toDto(user)).thenReturn(userResponseDto);

        UserResponseDto result = userController.updateUserRole(id, role);

        assertEquals(userResponseDto, result);
        verify(userService).getById(id);
        verify(userService).update(user);
        verify(mapper).toDto(user);
    }

    // Following the same pattern, you can add test methods for getMyProfileInfo and updateMyProfileInfo
    // For these methods, you will need to mock the Authentication object and the method getName()
    // Also you will have to ensure that UserRequestDto parameters match the expected values in updateMyProfileInfo
}
