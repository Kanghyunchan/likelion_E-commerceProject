package com.myproject.ecommerce_service.service.payment;

import com.myproject.ecommerce_service.domain.payment.Payment;
import com.myproject.ecommerce_service.dto.Payment.PaymentHistoryResponse;
import com.myproject.ecommerce_service.dto.Payment.PointBalanceResponse;
import com.myproject.ecommerce_service.dto.Payment.PointChargeRequest;
import com.myproject.ecommerce_service.repository.payment.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;

    @Transactional
    public void charge(PointChargeRequest request) {
        Payment payment = new Payment(
                null,
                request.getUserId(),
                null,
                request.getChargeAmount(),
                "CHARGE",
                LocalDateTime.now()
        );
        paymentRepository.save(payment);
    }

    @Transactional(readOnly = true)
    public PointBalanceResponse getBalance(Long userId) {
        int balance = paymentRepository.getUserBalance(userId);
        return new PointBalanceResponse(userId, balance);
    }

    @Transactional(readOnly = true)
    public List<PaymentHistoryResponse> getHistory(Long userId) {
        return paymentRepository.findByUserId(userId).stream()
                .map(p -> new PaymentHistoryResponse(
                        p.getPaymentId(),
                        p.getPaymentType(),
                        p.getAmount(),
                        p.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }

    @Transactional
    public void processOrderPayment(Long userId, Long orderId, int totalPrice) {
        int currentPoint = paymentRepository.getUserBalance(userId);

        if (currentPoint < totalPrice) {
            throw new IllegalStateException("포인트가 부족하여 결제할 수 없습니다. (현재 잔고: " + currentPoint + "원)");
        }

        Payment payment = new Payment(
                null,
                userId,
                orderId,
                -totalPrice,
                "PAYMENT",
                LocalDateTime.now()
        );
        paymentRepository.save(payment);
    }
}
