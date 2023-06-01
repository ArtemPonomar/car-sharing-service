package com.example.carsharingservice.service.impl;

import com.example.carsharingservice.model.Payment;
import com.example.carsharingservice.service.StripeService;
import com.stripe.Stripe;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripeServiceImpl implements StripeService {
    @Value("${STRIPE_SECRET_KEY}")
    private String stripeSecretKey;
    public final String PAYMENT_URL = "http://localhost:8080/payments";

    @Override
    public SessionCreateParams createPaymentSession(Long rentalId, Payment.Type type) {
        Stripe.apiKey = stripeSecretKey;

        SessionCreateParams.Builder builder = new SessionCreateParams.Builder();
        builder.addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD);
        builder.setMode(SessionCreateParams.Mode.PAYMENT);
        builder.setSuccessUrl(PAYMENT_URL + "?session_id={CHECKOUT_SESSION_ID}");
        builder.setCancelUrl(PAYMENT_URL + "/cancel");
        builder.addLineItem(
                new SessionCreateParams.LineItem.Builder()
                        .setPriceData(
                                new SessionCreateParams.LineItem.PriceData.Builder()
                                    .setCurrency("usd")
                                    .setProductData(
                                    new SessionCreateParams.LineItem.PriceData.ProductData.Builder()
                                        .setName("Car Rental Payment")
                                        .build()
                                        )
                                        .setUnitAmount(10000L)
                                        .build()
                        )
                        .setQuantity(1L)
                        .build()
        );
        SessionCreateParams params = builder.build();
        return params;
    }
}
