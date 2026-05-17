package com.myproject.ecommerce_service.service.order;

import com.myproject.ecommerce_service.domain.order.OrderItem;
import com.myproject.ecommerce_service.domain.order.OrderStatus;
import com.myproject.ecommerce_service.domain.order.Orders;
import com.myproject.ecommerce_service.domain.product.Product;
import com.myproject.ecommerce_service.dto.order.OrderCreateRequest;
import com.myproject.ecommerce_service.dto.order.OrderResponse;
import com.myproject.ecommerce_service.repository.order.OrderItemRepository;
import com.myproject.ecommerce_service.repository.order.OrderRepository;
import com.myproject.ecommerce_service.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Transactional
    public OrderResponse createOrder(OrderCreateRequest request){
        int totalPrice = 0;

        for(OrderCreateRequest.OrderItemElement item : request.getOrderItems()){
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다. ID: " + item.getProductId()));

            if(product.getQuantity() < item.getQuantity()) {
                throw new IllegalStateException("[" + product.getProductName() + "] 상품의 재고가 부족합니다. (현재 재고: " + product.getQuantity() + "개)");
            }

            int updatedQuantity = product.getQuantity() - item.getQuantity();
            productRepository.updateQuantity(product.getProductId(), item.getQuantity());

            totalPrice += product.getPrice() * item.getQuantity();
        }
        Orders orders = new Orders(
                null,
                request.getUserId(),
                totalPrice,
                LocalDateTime.now(),
                OrderStatus.ORDERED,
                "충남 두정동"
        );

        Orders registerOrder = orderRepository.registration(orders);
        Long generatedOrderId = registerOrder.getOrderId();

        for(OrderCreateRequest.OrderItemElement item : request.getOrderItems()){
            Product product = productRepository.findById(item.getProductId()).get();

            OrderItem orderItem = new OrderItem(
                    null,
                    generatedOrderId,
                    item.getProductId(),
                    item.getQuantity(),
                    product.getPrice()
            );

            orderItemRepository.registration(orderItem);
        }
        return new OrderResponse(generatedOrderId, registerOrder.getTotalPrice(), registerOrder.getOrderStatus());
    }
}
