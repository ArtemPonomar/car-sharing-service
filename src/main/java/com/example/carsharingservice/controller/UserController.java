package com.example.carsharingservice.controller;

import com.example.carsharingservice.dto.mapper.DtoMapper;
import com.example.carsharingservice.dto.request.UserRequestDto;
import com.example.carsharingservice.dto.response.UserResponseDto;
import com.example.carsharingservice.model.User;
import com.example.carsharingservice.service.UserService;
import lombok.RequiredArgsConstructor;
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

    private final UserService userService;
    private final DtoMapper<User, UserRequestDto, UserResponseDto> mapper;

    @PutMapping("/{id}/role")
    public UserResponseDto updateUserRole(@PathVariable Long id, @RequestParam User.Role userRole) {
        User foundUser = userService.getById(id);
        foundUser.setRole(userRole);
        return mapper.toDto(userService.update(foundUser));
    }

    @PutMapping("/me")
    public UserResponseDto updateUserRole(@RequestBody UserRequestDto userRequestDto) {
        return mapper.toDto(userService.update(mapper.toModel(userRequestDto)));
    }

    @GetMapping("/me")
    public UserResponseDto getMyProfileInfo(@PathVariable Long id) {
        return mapper.toDto(userService.getById(id));
    }
}
