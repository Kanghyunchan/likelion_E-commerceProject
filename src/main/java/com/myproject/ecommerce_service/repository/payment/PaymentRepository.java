package com.myproject.ecommerce_service.repository.payment;

import com.myproject.ecommerce_service.domain.payment.Payment;

import java.util.List;

public interface PaymentRepository {
    void save(Payment payment);
    int getUserBalance(Long userId);
    List<Payment> findByUserId(Long userId);
}
