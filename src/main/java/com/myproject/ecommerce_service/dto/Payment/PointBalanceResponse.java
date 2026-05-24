package com.myproject.ecommerce_service.dto.Payment;

import com.myproject.ecommerce_service.domain.order.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentResponse {
    private Long orderId;
    private int amount;
}
