package com.myproject.ecommerce_service.dto.Payment;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class PaymentHistoryResponse {
    private Long paymentId;
    private String paymentType;
    private int amount;
    private LocalDateTime createdAt;
}
