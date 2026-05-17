package com.myproject.ecommerce_service.dto.order;

import com.myproject.ecommerce_service.domain.order.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderResponse {
    private Long orderId;
    private int totalPrice;
    private OrderStatus Status;
}
