package com.example.carsharingservice.controller;

import com.example.carsharingservice.dto.mapper.DtoMapper;
import com.example.carsharingservice.dto.request.UserRequestDto;
import com.example.carsharingservice.dto.response.UserResponseDto;
import com.example.carsharingservice.model.User;
import com.example.carsharingservice.service.MessagingService;
import com.example.carsharingservice.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final MessagingService messagingService;
    private final UserService userService;
    private final DtoMapper<User, UserRequestDto, UserResponseDto> mapper;

    @PutMapping("/{id}/role")
    @Operation(summary = "Change role for user")
    public UserResponseDto updateUserRole(@PathVariable Long id, @RequestParam User.Role userRole) {
        User foundUser = userService.getById(id);
        foundUser.setRole(userRole);
        return mapper.toDto(userService.update(foundUser));
    }

    @GetMapping("/me")
    @Operation(summary = "Get info about authorized user")
    public UserResponseDto getMyProfileInfo(Authentication auth) {
        String name = auth.getName();
        return mapper.toDto(userService.getByEmail(name));
    }

    @PutMapping("/me")
    @Operation(summary = "Update info about authorized user")
    public UserResponseDto updateMyProfileInfo(@RequestBody UserRequestDto userRequestDto) {
        User user = userService.getByEmail(userRequestDto.getEmail());
        user.setEmail(userRequestDto.getEmail());
        user.setTelegramId(userRequestDto.getTelegramId());
        user.setFirstName(userRequestDto.getFirstName());
        user.setLastName(userRequestDto.getLastName());
        return mapper.toDto(userService.update(user));
    }
}
