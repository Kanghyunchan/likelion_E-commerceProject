package com.myproject.ecommerce_service.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CartItemResponse {
    private Long cartItemId;
    private Long productId;
    private String productName;
    private int price;
    private int quantity;
}
