package com.example.carsharingservice.service;

public interface MessagingService {
    void sendMessage(String message, Long destination);
    void sendMessageToDevChat(String message);
}
