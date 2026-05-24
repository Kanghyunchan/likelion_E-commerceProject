package com.myproject.ecommerce_service.controller.payment;

import com.myproject.ecommerce_service.dto.Payment.PaymentHistoryResponse;
import com.myproject.ecommerce_service.dto.Payment.PointChargeRequest;
import com.myproject.ecommerce_service.dto.Payment.PointBalanceResponse;
import com.myproject.ecommerce_service.service.order.OrderService;
import com.myproject.ecommerce_service.service.payment.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Payment", description = "포인트 결제 관련 API")
@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;

    @Operation(summary = "포인트 충전", description = "사용자의 포인트를 충전하고 내역을 생성합니다.")
    @PostMapping("/charge")
    public ResponseEntity<Void> chargePoint(@RequestBody PointChargeRequest request) {
        paymentService.charge(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "잔액 조회", description = "사용자의 포인트 잔액을 조회합니다.")
    @GetMapping("/balance")
    public ResponseEntity<PointBalanceResponse> getBalance(@RequestParam Long userId) {
        PointBalanceResponse response = paymentService.getBalance(userId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "결제 내역 조회", description = "사용자의 전체 포인트 입출금 내역을 조회합니다.")
    @GetMapping("/history")
    public ResponseEntity<List<PaymentHistoryResponse>> getHistory(@RequestParam Long userId) {
        List<PaymentHistoryResponse> history = paymentService.getHistory(userId);
        return ResponseEntity.ok(history);
    }
}
