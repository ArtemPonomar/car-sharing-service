package com.example.carsharingservice.controller;

import com.example.carsharingservice.dto.request.PaymentInfoRequestDto;
import com.example.carsharingservice.model.Payment;
import com.example.carsharingservice.service.PaymentService;
import com.example.carsharingservice.service.RentalService;
import com.example.carsharingservice.service.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@AllArgsConstructor
public class PaymentController {
    private final StripeService stripeService;
    private final RentalService rentalService;
    private final PaymentService paymentService;

    @GetMapping("/payment")
    public RedirectView createStripeSession(
            @RequestBody PaymentInfoRequestDto paymentInfoRequestDto) {
        SessionCreateParams params = stripeService.createPaymentSession(
                paymentInfoRequestDto.getRentalId());
        try {
            Session session = Session.create(params);
            String sessionUrl = session.getUrl();
            String sessionId = session.getId();
            BigDecimal amountToPay = BigDecimal.valueOf(session.getAmountTotal());
            Payment payment = new Payment();
            payment.setSessionId(sessionId);
            payment.setUrl(new URL(sessionUrl));
            payment.setRental(rentalService.getById(paymentInfoRequestDto.getRentalId()));
            payment.setPaymentAmount(amountToPay);
            paymentService.save(payment);
            return new RedirectView(sessionUrl);
        } catch (StripeException | MalformedURLException e) {
            e.printStackTrace();
            return new RedirectView("/error");
        }
    }
}
