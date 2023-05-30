package com.example.carsharingservice.dto.mapper.impl;

import com.example.carsharingservice.dto.mapper.DtoMapper;
import com.example.carsharingservice.dto.request.PaymentRequestDto;
import com.example.carsharingservice.dto.response.PaymentResponseDto;
import com.example.carsharingservice.model.Payment;
import java.net.MalformedURLException;
import java.net.URL;

public class PaymentMapper implements DtoMapper<Payment, PaymentRequestDto, PaymentResponseDto> {
    @Override
    public Payment toModel(PaymentRequestDto requestDto) {
        try {
            Payment payment = new Payment();
            payment.setSessionId(requestDto.getSessionId());
            payment.setUrl(new URL(requestDto.getUrl()));
            payment.setPaymentAmount(requestDto.getPaymentAmount());
            payment.setType(requestDto.getType());
            payment.setStatus(requestDto.getStatus());
            return payment;
        } catch (MalformedURLException e) {
            throw new RuntimeException("Can't parse string: " + requestDto.getUrl() + " to URL", e);
        }
    }

    @Override
    public PaymentResponseDto toDto(Payment model) {
        PaymentResponseDto paymentResponseDto = new PaymentResponseDto();
        paymentResponseDto.setRentalId(model.getRentalId());
        paymentResponseDto.setSessionId(model.getSessionId());
        paymentResponseDto.setUrl(model.getUrl().toString());
        paymentResponseDto.setPaymentAmount(model.getPaymentAmount());
        paymentResponseDto.setType(model.getType());
        paymentResponseDto.setStatus(model.getStatus());
        return paymentResponseDto;
    }
}
