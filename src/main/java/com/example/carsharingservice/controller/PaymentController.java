package com.example.carsharingservice.controller;

import com.example.carsharingservice.dto.mapper.impl.PaymentMapper;
import com.example.carsharingservice.dto.request.PaymentInfoRequestDto;
import com.example.carsharingservice.dto.response.PaymentResponseDto;
import com.example.carsharingservice.model.Payment;
import com.example.carsharingservice.service.MessagingService;
import com.example.carsharingservice.service.PaymentService;
import com.example.carsharingservice.service.StripeService;
import com.example.carsharingservice.service.UserService;
import com.stripe.param.checkout.SessionCreateParams;
import io.swagger.v3.oas.annotations.Operation;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
    private final MessagingService messagingService;
    private final UserService userService;

    @PostMapping
    @Operation(summary = "Create payment session and get URL for payment")
    public PaymentResponseDto createStripeSession(
            @RequestBody PaymentInfoRequestDto paymentInfoRequestDto) {
        SessionCreateParams params = stripeService.createPaymentSession(
                paymentInfoRequestDto.getRentalId(), paymentInfoRequestDto.getType());
        return stripeService.getPaymentFromSession(params, paymentInfoRequestDto);
    }

    @GetMapping("/success")
    @Operation(summary = "Payment success page")
    public String success(
            @RequestParam("session_id") String sessionId
    ) {
        Payment payment = paymentService.findBySessionId(sessionId);

        if (paymentService.isSessionPaid(sessionId)) {
            messagingService.sendMessageToUser(
                    "invalid payment",
                    userService.findUserByPaymentId(payment.getId()));
            return "invalid payment";
        }

        payment.setStatus(Payment.Status.PAID);
        paymentService.update(payment);

        messagingService.sendMessageToUser(
                "Your payment for car %s %s was successful!\namount: %s".formatted(
                                payment.getRental().getCar().getBrand(),
                                payment.getRental().getCar().getModel(),
                                payment.getPaymentAmount()),
                userService.findUserByPaymentId(payment.getId()));

        return "Your payment was successful!";
    }

    @GetMapping("/cancel")
    @Operation(summary = "Cancel payment")
    public String cancelPayment() {
        return "Payment was canceled! But this payment page active for 24 hours.";
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get list of payments by user id")
    public List<PaymentResponseDto> getUserPayments(@PathVariable Long id) {
        return paymentService.getPaymentsByUserId(id).stream()
                .map(mapper::toDto)
                .collect(Collectors.toList());
    }
}
