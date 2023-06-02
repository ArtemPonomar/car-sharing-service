package com.example.carsharingservice.dto.request;

import com.example.carsharingservice.model.Payment;
import lombok.Data;

@Data
public class PaymentInfoRequestDto {
    private Long rentalId;
    private Payment.Type type;
}
