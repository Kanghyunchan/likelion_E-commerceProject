package com.myproject.ecommerce_service.dto.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ProductRegisterResponse {
    private Long productId;
    private String productName;
    private int price;
    private int quantity;
}
