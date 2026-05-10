package com.myproject.ecommerce_service.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor

public class CartResponse {
    private Long cartItemId;
    private String productName;
    private int quantity;
}
