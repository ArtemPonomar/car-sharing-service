package com.example.carsharingservice.dto.response;

import com.example.carsharingservice.model.Payment;
import java.math.BigDecimal;
import java.net.URL;
import lombok.Data;

@Data
public class PaymentResponseDto {
    private Long id;
    private Long rentalId;
    private String sessionId;
    private URL url;
    private BigDecimal paymentAmount;
    private Payment.Type type;
    private Payment.Status status;
}
