package com.myproject.ecommerce_service.repository.order;

import com.myproject.ecommerce_service.domain.order.Orders;

import java.util.Optional;

public interface OrderRepository {
    Orders registration(Orders orders);
    Optional<Orders> findById(Long orderId);
    void update(Orders orders);
    void updateStatus(Orders orders);
}
