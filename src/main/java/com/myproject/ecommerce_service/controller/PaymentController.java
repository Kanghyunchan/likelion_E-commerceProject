package com.myproject.ecommerce_service.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Payment", description = "결제 관련 API")
@RestController
@RequestMapping("/api/payments")
public class PaymentController {
    @Operation(summary = "결제 요청")
    @PostMapping
    public String requestPayment(@RequestParam Long orderId){
        return orderId + "번 주문에 대한 결제가 완료되었습니다.";
    }
}
