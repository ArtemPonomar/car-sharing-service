package com.example.carsharingservice.service.impl;

import com.example.carsharingservice.service.MessagingService;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class TelegramMessagingService implements MessagingService {
    private final String token;
    private final Long devChatId;

    public TelegramMessagingService(@Value("${telegram.bot.token}") String token,
                                    @Value("${telegram.bot.devChatId}") Long devChatId) {
        this.token = token;
        this.devChatId = devChatId;
    }

    public void sendMessageToDevChat(String message) {
        sendMessage(message, devChatId);
    }

    public void sendMessage(String text, Long destination) {
        try {
            HttpURLConnection connection = (HttpURLConnection) new URL("https://api.telegram.org/bot" + token + "/sendMessage").openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json; utf-8");
            connection.setRequestProperty("Accept", "application/json");
            connection.setConnectTimeout(5000);
            connection.setDoOutput(true);

            final SendMessage message = new SendMessage(devChatId, text);
            String json = new GsonBuilder().create().toJson(message);
            try (OutputStream outputStream = connection.getOutputStream()) {
                byte[] input = json.getBytes("utf-8");
                outputStream.write(input, 0, input.length);
            }

            try (BufferedReader br = new BufferedReader(
                    new InputStreamReader(connection.getInputStream(), "utf-8"))) {
                StringBuilder response = new StringBuilder();
                String responseLine = null;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static class SendMessage {
        private Long chatId;
        private String text;

        public SendMessage(Long chatId, String text) {
            this.chatId = chatId;
            this.text = text;
        }
    }
}
