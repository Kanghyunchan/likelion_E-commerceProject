package com.myproject.ecommerce_service.domain.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    private Long cartId;
    private Long userId;
}
