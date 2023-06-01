package com.example.carsharingservice.service.impl;

import com.example.carsharingservice.exception.DataException;
import com.example.carsharingservice.model.User;
import com.example.carsharingservice.service.MessagingService;
import com.example.carsharingservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
@RequiredArgsConstructor
public class TelegramMessagingService
        extends TelegramLongPollingBot
        implements MessagingService {

    @Value("${telegram.bot.name}")
    private String botName;
    @Value("${telegram.bot.token}")
    private String botToken;
    private final UserService userService;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            SendMessage response = new SendMessage();
            response.setChatId(update.getMessage().getChatId());
            if (update.getMessage().getText().startsWith("/subscribe")) {
                try {
                    User user = userService.getByEmail(update.getMessage().getText().split(" ")[1]);
                    user.setTelegramId(update.getMessage().getChatId());
                    userService.update(user);
                } catch (DataException e) {
                    response.setText(e.getMessage());
                    try {
                        execute(response);
                        return;
                    } catch (TelegramApiException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                response.setText("You have successfully subscribed to messaging service!");
            } else {
                response.setText("Unrecognized command! \n"
                        + "Use this command to subscribe: \n"
                        + "/subscribe your.email@gmail.com");
            }
            try {
                execute(response);
            } catch (TelegramApiException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void sendMessageToUser(String text, User user) {
        if (user.getTelegramId() == null) {
            System.out.printf("Cannot send message to user with id"
                    + " %d no telegramId detected.\n", user.getId());
            return;
        }
        try {
            SendMessage message = new SendMessage();
            message.setText(text);
            message.setChatId(user.getTelegramId());
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
