package com.myproject.ecommerce_service.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CartAddRequest {
    private Long userId;
    private Long productId;
    private int quantity;
}
