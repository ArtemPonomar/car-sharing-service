package com.example.carsharingservice.dto.mapper.impl;

import com.example.carsharingservice.dto.mapper.DtoMapper;
import com.example.carsharingservice.dto.request.PaymentRequestDto;
import com.example.carsharingservice.dto.response.PaymentResponseDto;
import com.example.carsharingservice.model.Payment;
import com.example.carsharingservice.service.PaymentService;
import com.example.carsharingservice.service.RentalService;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper implements DtoMapper<Payment, PaymentRequestDto, PaymentResponseDto> {
    private final RentalService rentalService;

    public PaymentMapper(PaymentService paymentService, RentalService rentalService) {
        this.rentalService = rentalService;
    }

    @Override
    public Payment toModel(PaymentRequestDto requestDto) {
        Payment payment = new Payment();
        payment.setRental(rentalService.getById(requestDto.getRentalId()));
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
        paymentResponseDto.setRentalId(model.getRental().getId());
        paymentResponseDto.setSessionId(model.getSessionId());
        paymentResponseDto.setUrl(model.getUrl());
        paymentResponseDto.setPaymentAmount(model.getPaymentAmount());
        paymentResponseDto.setType(model.getType());
        paymentResponseDto.setStatus(model.getStatus());
        return paymentResponseDto;
    }
}
