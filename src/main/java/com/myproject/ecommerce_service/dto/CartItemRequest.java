package com.myproject.ecommerce_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CartItemRequest {
    private Long productId;
    private int quantity;
}
