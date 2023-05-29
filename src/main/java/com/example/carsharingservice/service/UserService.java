package com.example.carsharingservice.service;

import java.util.List;
import com.example.carsharingservice.model.Car;
import com.example.carsharingservice.model.User;

public interface UserService {
    User add(User user);

    User getById(Long id);

    void delete(Long id);

    User update(User user);

    List<User> getAll();
}
