package com.example.carsharingservice.service.impl;

import com.example.carsharingservice.service.StripeService;
import com.stripe.Stripe;
import com.stripe.param.checkout.SessionCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripeServiceImpl implements StripeService {
    @Value("${STRIPE_SECRET_KEY}")
    private String stripeSecretKey;

    @Override
    public SessionCreateParams createPaymentSession(Long rentalId) {
        Stripe.apiKey = stripeSecretKey;

        SessionCreateParams.Builder builder = new SessionCreateParams.Builder();
        builder.addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD);
        builder.setMode(SessionCreateParams.Mode.PAYMENT);
        builder.setSuccessUrl("http://localhost:8080/cars");
        builder.setCancelUrl("http://localhost:8080/rental");
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
