package com.example.carsharingservice.dto.request;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class PaymentRequestDto {
    private String sessionId;
    private String url;
    private BigDecimal paymentAmount;
    private String type;
    private String status;
}
