package com.example.carsharingservice.service;

import com.example.carsharingservice.model.User;
import java.util.List;

public interface UserService {
    User add(User user);

    User getById(Long id);

    User getByEmail(String email);

    void delete(Long id);

    User update(User user);

    List<User> getAll();

    User findUserByPaymentId(Long paymentId);
}
