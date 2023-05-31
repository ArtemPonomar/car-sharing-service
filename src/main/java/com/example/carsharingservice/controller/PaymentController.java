package com.example.carsharingservice.controller;

import com.example.carsharingservice.service.StripeService;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@AllArgsConstructor
public class PaymentController {
    private final StripeService stripeService;

    @GetMapping("/payment")
    public RedirectView createStripeSession() {
        SessionCreateParams params = stripeService.createPaymentSession();
        try {
            Session session = Session.create(params);
            String sessionUrl = session.getUrl();
            String sessionId = session.getId();
            BigDecimal amountToPay = BigDecimal.valueOf(session.getAmountTotal());
            return new RedirectView(sessionUrl);
        } catch (StripeException e) {
            e.printStackTrace();
            return new RedirectView("/error");
        }
    }
}
