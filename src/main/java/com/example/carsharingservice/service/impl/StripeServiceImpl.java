package com.example.carsharingservice.service.impl;

import com.example.carsharingservice.dto.mapper.impl.PaymentMapper;
import com.example.carsharingservice.dto.request.PaymentInfoRequestDto;
import com.example.carsharingservice.dto.request.PaymentRequestDto;
import com.example.carsharingservice.dto.response.PaymentResponseDto;
import com.example.carsharingservice.model.Payment;
import com.example.carsharingservice.service.PaymentService;
import com.example.carsharingservice.service.StripeService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StripeServiceImpl implements StripeService {
    private static final String PAYMENT_URL = "http://localhost:6868/payments";
    private final PaymentService paymentService;
    private final PaymentMapper mapper;
    @Value("${STRIPE_SECRET_KEY}")
    private String stripeSecretKey;

    @Override
    public SessionCreateParams createPaymentSession(Long rentalId, Payment.Type type) {
        Stripe.apiKey = stripeSecretKey;

        SessionCreateParams.Builder builder = new SessionCreateParams.Builder();
        builder.addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD);
        builder.setMode(SessionCreateParams.Mode.PAYMENT);
        builder.setSuccessUrl(PAYMENT_URL + "/success" + "?session_id={CHECKOUT_SESSION_ID}");
        builder.setCancelUrl(PAYMENT_URL + "/cancel");
        builder.addLineItem(
                new SessionCreateParams.LineItem.Builder()
                        .setPriceData(
                                new SessionCreateParams.LineItem.PriceData.Builder()
                                        .setCurrency("usd")
                                        .setProductData(
                                                new SessionCreateParams.LineItem
                                                        .PriceData.ProductData.Builder()
                                                        .setName("Car Rental Payment")
                                                        .build()
                                        )
                                        .setUnitAmount(
                                                paymentService
                                                        .calculatePaymentAmount(rentalId, type)
                                                        .longValue())
                                        .build()
                        )
                        .setQuantity(1L)
                        .build()
        );
        SessionCreateParams params = builder.build();
        return params;
    }

    @Override
    public PaymentResponseDto getPaymentFromSession(SessionCreateParams params,
                                                    PaymentInfoRequestDto paymentInfoRequestDto) {
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
}
