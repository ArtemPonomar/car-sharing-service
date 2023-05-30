package com.example.carsharingservice.dto.request;

import com.example.carsharingservice.model.Payment;
import java.math.BigDecimal;
import lombok.Data;

@Data
public class PaymentRequestDto {
    private String sessionId;
    private String url;
    private BigDecimal paymentAmount;
    private Payment.Type type;
    private Payment.Status status;
}
