package com.example.carsharingservice.service;

import com.example.carsharingservice.model.User;

public interface MessagingService {

    void sendMessageToUser(String message, User user);
}
