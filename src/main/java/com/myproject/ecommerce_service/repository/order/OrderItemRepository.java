package com.myproject.ecommerce_service.repository.order;

import com.myproject.ecommerce_service.domain.order.OrderItem;

import java.util.List;

public interface OrderItemRepository {
    OrderItem registration(OrderItem orderItem);
    List<OrderItem> findByOrderId(Long orderId);
}
