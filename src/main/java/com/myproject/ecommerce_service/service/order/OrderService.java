package com.myproject.ecommerce_service.service.order;

import com.myproject.ecommerce_service.domain.order.OrderItem;
import com.myproject.ecommerce_service.domain.order.OrderStatus;
import com.myproject.ecommerce_service.domain.order.Orders;
import com.myproject.ecommerce_service.domain.product.Product;
import com.myproject.ecommerce_service.dto.Payment.PaymentRequest;
import com.myproject.ecommerce_service.dto.Payment.PointChargeRequest;
import com.myproject.ecommerce_service.dto.Payment.PointBalanceResponse;
import com.myproject.ecommerce_service.dto.order.OrderCreateRequest;
import com.myproject.ecommerce_service.dto.order.OrderResponse;
import com.myproject.ecommerce_service.repository.order.OrderItemRepository;
import com.myproject.ecommerce_service.repository.order.OrderRepository;
import com.myproject.ecommerce_service.repository.product.ProductRepository;
import com.myproject.ecommerce_service.service.payment.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final PaymentService paymentService;

    @Transactional
    public OrderResponse createOrder(OrderCreateRequest request){
        int totalPrice = 0;

        Map<Long, Product> productMap = new HashMap<>();

        for(OrderCreateRequest.OrderItemElement item : request.getOrderItems()){
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다. ID: " + item.getProductId()));

            if(product.getQuantity() < item.getQuantity()) {
                throw new IllegalStateException("[" + product.getProductName() + "] 상품의 재고가 부족합니다. (현재 재고: " + product.getQuantity() + "개)");
            }

            int updatedQuantity = product.getQuantity() - item.getQuantity();
            productRepository.updateQuantity(product.getProductId(), updatedQuantity);

            totalPrice += product.getPrice() * item.getQuantity();

            productMap.put(product.getProductId(), product);
        }
        Orders orders = new Orders(
                null,
                request.getUserId(),
                totalPrice,
                LocalDateTime.now(),
                OrderStatus.ORDERED,
                request.getShippingAddress()
        );

        Orders registerOrder = orderRepository.registration(orders);
        Long generatedOrderId = registerOrder.getOrderId();

        for(OrderCreateRequest.OrderItemElement item : request.getOrderItems()){
            Product product = productMap.get(item.getProductId());
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

    @Transactional
    public void payOrder(PaymentRequest request) {
        Orders orders = orderRepository.findById(request.getOrderId())
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 주문입니다. ID: " + request.getOrderId()));

        if (orders.getOrderStatus() != OrderStatus.ORDERED) {
            throw new IllegalStateException("결제를 진행할 수 없는 주문 상태입니다. 현재 상태: " + orders.getOrderStatus());
        }

        paymentService.processOrderPayment(orders.getUserId(), orders.getOrderId(), orders.getTotalPrice());

        orders.paymentComplete();
        orderRepository.updateStatus(orders);
    }
}
