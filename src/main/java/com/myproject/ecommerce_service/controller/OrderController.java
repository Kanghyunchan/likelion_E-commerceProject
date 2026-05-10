package com.myproject.ecommerce_service.controller;

import com.myproject.ecommerce_service.dto.OrderResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Order", description = "주문 관련 API")
@RestController
@RequestMapping("/api/orders")
public class OrderController {
    @Operation(summary = "주문 생성")
    @PostMapping
    public OrderResponse createOrder() {
        return new OrderResponse(501L, 23000, "ORDERED");
    }

    @Operation(summary = "주문 상세 조회")
    @GetMapping("/{orderId}")
    public OrderResponse getOrder(@PathVariable Long orderId) {
        return new OrderResponse(orderId, 23000, "SHIPPING");
    }
}
