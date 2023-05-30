package com.example.carsharingservice.dto.response;

import java.math.BigDecimal;
import lombok.Data;

@Data
public class PaymentResponseDto {
    private Long rentalId;
    private String sessionId;
    private String url;
    private BigDecimal paymentAmount;
    private String type;
    private String status;
}
