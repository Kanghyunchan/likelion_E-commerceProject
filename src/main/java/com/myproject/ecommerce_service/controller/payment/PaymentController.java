package com.myproject.ecommerce_service.controller.payment;

import com.myproject.ecommerce_service.dto.Payment.PaymentRequest;
import com.myproject.ecommerce_service.dto.Payment.PaymentResponse;
import com.myproject.ecommerce_service.service.order.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Payment", description = "결제 관련 API")
@RestController
@RequestMapping("/api/payments/")
@RequiredArgsConstructor
public class PaymentController {
    private final OrderService orderService;

    @Operation(summary = "결제 요청 및 완료", description = "생성된 주문 건을 결제 처리 후 DB에 반영")
    @PostMapping
    public ResponseEntity<PaymentResponse> processPayment(@RequestBody PaymentRequest request){
        PaymentResponse response = orderService.paymentProcess(request);
        return ResponseEntity.ok(response);
    }
}
