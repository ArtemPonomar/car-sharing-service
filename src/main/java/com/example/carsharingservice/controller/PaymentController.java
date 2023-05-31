package com.example.carsharingservice.controller;

import java.util.List;
import java.util.stream.Collectors;
import com.example.carsharingservice.dto.mapper.DtoMapper;
import com.example.carsharingservice.dto.request.PaymentRequestDto;
import com.example.carsharingservice.dto.response.PaymentResponseDto;
import com.example.carsharingservice.model.Payment;
import com.example.carsharingservice.service.PaymentService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@AllArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    private final DtoMapper<Payment, PaymentRequestDto, PaymentResponseDto> mapper;

    @GetMapping
    public List<PaymentResponseDto> getUserPayments(@RequestParam Long userId) {
        return paymentService.getPaymentsByUserId(userId).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/success/{id}")
    @ResponseStatus(HttpStatus.OK)
    public String checkSuccessfulPayment(@PathVariable Long id) {
        Payment payment = paymentService.findById(id);
        payment.setStatus(Payment.Status.PAID);
        paymentService.update(payment);
        return "Payment successful";
    }

    @GetMapping("/cancel/{id}")
    public String returnPaymentPausedMessage(@PathVariable Long id) {
        Payment payment = paymentService.findById(id);
        payment.setStatus(Payment.Status.PENDING);
        paymentService.update(payment);
        return "Payment paused";
    }


}
