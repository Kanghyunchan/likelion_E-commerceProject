package com.myproject.ecommerce_service.domain.order;

import lombok.Getter;

@Getter
public enum OrderStatus {
    ORDERED("결제 대기"),
    PAYMENT_COMPLETE("결제 완료"),
    SHIPPING("배송중"),
    DELIVERED("배송 완료"),
    CANCELED("주문 취소");

    private final String description;

    OrderStatus(String description){
        this.description = description;
    }
}
