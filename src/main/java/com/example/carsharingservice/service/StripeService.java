package com.example.carsharingservice.service;

import com.stripe.param.checkout.SessionCreateParams;

public interface StripeService {
    SessionCreateParams createPaymentSession();
}
