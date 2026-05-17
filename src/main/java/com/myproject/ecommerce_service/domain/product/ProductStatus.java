package com.myproject.ecommerce_service.domain.product;

import lombok.Getter;

@Getter
public enum ProductStatus {
    SELL("판매중"),
    SOLD_OUT("품절"),
    HIDDEN("숨김");

    private final String description;

    ProductStatus(String description){
        this.description = description;
    }
}
