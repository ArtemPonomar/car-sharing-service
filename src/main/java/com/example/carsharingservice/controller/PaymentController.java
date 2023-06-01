package com.example.carsharingservice.controller;

import com.example.carsharingservice.dto.mapper.impl.PaymentMapper;
import com.example.carsharingservice.dto.request.PaymentInfoRequestDto;
import com.example.carsharingservice.dto.request.PaymentRequestDto;
import com.example.carsharingservice.dto.response.PaymentResponseDto;
import com.example.carsharingservice.model.Payment;
import com.example.carsharingservice.service.PaymentService;
import com.example.carsharingservice.service.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final StripeService stripeService;
    private final PaymentService paymentService;
    private final PaymentMapper mapper;

    @GetMapping
    @Operation(summary = "Create payment session and get URL for payment")
    public PaymentResponseDto createStripeSession(
            @RequestBody PaymentInfoRequestDto paymentInfoRequestDto) {
        SessionCreateParams params = stripeService.createPaymentSession(
                paymentInfoRequestDto.getRentalId(), paymentInfoRequestDto.getType());
        try {
            Session session = Session.create(params);
            String sessionUrl = session.getUrl();
            String sessionId = session.getId();
            BigDecimal amountToPay = BigDecimal.valueOf(session.getAmountTotal());
            PaymentRequestDto requestDto = new PaymentRequestDto();
            requestDto.setSessionId(sessionId);
            requestDto.setUrl(new URL(sessionUrl));
            requestDto.setType(paymentInfoRequestDto.getType());
            requestDto.setStatus(Payment.Status.PENDING);
            requestDto.setPaymentAmount(amountToPay.divide(BigDecimal.valueOf(100)));
            requestDto.setRentalId(paymentInfoRequestDto.getRentalId());

            return mapper.toDto(paymentService.save(mapper.toModel(requestDto)));
        } catch (StripeException | MalformedURLException e) {
            throw new RuntimeException("Can't get payment page.", e);
        }
    }

    @GetMapping("/success")
    @Operation(summary = "Payment success page")
    public String success(
            @RequestParam("session_id") String sessionId
    ) {
        Payment payment = paymentService.findBySessionId(sessionId);

        if (!paymentService.isSessionPaid(sessionId)) {
            return "invalid payment";
        }

        payment.setStatus(Payment.Status.PAID);
        paymentService.update(payment);

        return "Your payment was successful!";
    }

    @GetMapping
    @Operation(summary = "Get list of payments by user id")
    public List<PaymentResponseDto> getUserPayments(@RequestParam Long userId) {
        return paymentService.getPaymentsByUserId(userId).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
