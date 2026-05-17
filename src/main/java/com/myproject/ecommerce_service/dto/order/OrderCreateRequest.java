package com.myproject.ecommerce_service.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class OrderCreateRequest {
    private Long userId;
    private List<OrderItemElement> orderItems;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class OrderItemElement{
        private Long productId;
        private int quantity;
    }
}
