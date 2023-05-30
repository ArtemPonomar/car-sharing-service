package com.example.carsharingservice.dto.mapper.impl;

import com.example.carsharingservice.dto.mapper.DtoMapper;
import com.example.carsharingservice.dto.request.PaymentRequestDto;
import com.example.carsharingservice.dto.response.PaymentResponseDto;
import com.example.carsharingservice.model.Payment;

public class PaymentMapper implements DtoMapper<Payment, PaymentRequestDto, PaymentResponseDto> {
    @Override
    public Payment toModel(PaymentRequestDto requestDto) {
        Payment payment = new Payment();
        payment.setRentalId(requestDto.getRentalID());
        payment.setSessionId(requestDto.getSessionId());
        payment.setUrl(requestDto.getUrl());
        payment.setPaymentAmount(requestDto.getPaymentAmount());
        payment.setType(requestDto.getType());
        payment.setStatus(requestDto.getStatus());
        return payment;
    }

    @Override
    public PaymentResponseDto toDto(Payment model) {
        PaymentResponseDto paymentResponseDto = new PaymentResponseDto();
        paymentResponseDto.setId(model.getId());
        paymentResponseDto.setRentalId(model.getRentalId());
        paymentResponseDto.setSessionId(model.getSessionId());
        paymentResponseDto.setUrl(model.getUrl());
        paymentResponseDto.setPaymentAmount(model.getPaymentAmount());
        paymentResponseDto.setType(model.getType());
        paymentResponseDto.setStatus(model.getStatus());
        return paymentResponseDto;
    }
}
