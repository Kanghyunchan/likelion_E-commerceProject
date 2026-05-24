package com.myproject.ecommerce_service.domain.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class Payment {
    private Long paymentId;
    private Long userId;
    private Long orderId;
    private int amount;
    private String paymentType;
    private LocalDateTime createdAt;
}
