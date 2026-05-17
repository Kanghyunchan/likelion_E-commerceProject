package com.myproject.ecommerce_service.domain.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Orders {
    private Long orderId;
    private Long userId;
    private int totalPrice;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private String shippingAddress;

    public void changeShippingAddress(String shippingAddress){
        this.shippingAddress = shippingAddress;
    }

    public void ordered(){ orderStatus = OrderStatus.ORDERED; }
    public void canceled(){ orderStatus = OrderStatus.CANCELED; }
    public void delivered() { orderStatus = OrderStatus.DELIVERED; }
    public void paymentComplete(){ orderStatus = OrderStatus.PAYMENT_COMPLETE; }
    public void shipping(){ orderStatus = OrderStatus.SHIPPING; }
}
