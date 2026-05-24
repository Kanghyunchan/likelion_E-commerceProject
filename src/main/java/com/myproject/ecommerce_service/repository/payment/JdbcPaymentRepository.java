package com.myproject.ecommerce_service.repository.payment;

import com.myproject.ecommerce_service.domain.payment.Payment;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcPaymentRepository implements PaymentRepository{
    private final JdbcTemplate jdbcTemplate;

    @Override
    public void save(Payment payment) {
        String sql = "INSERT INTO payment (user_id, order_id, amount, payment_type, created_at) VALUES (?, ?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                payment.getUserId(),
                payment.getOrderId(),
                payment.getAmount(),
                payment.getPaymentType(),
                payment.getCreatedAt()
        );
    }

    @Override
    public int getUserBalance(Long userId) {
        String sql = "SELECT COALESCE(SUM(amount), 0) FROM payment WHERE user_id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, userId);
    }

    @Override
    public List<Payment> findByUserId(Long userId) {
        String sql = "SELECT * FROM payment WHERE user_id = ? ORDER BY created_at DESC";
        return jdbcTemplate.query(sql, paymentRowMapper(), userId);
    }

    private RowMapper<Payment> paymentRowMapper() {
        return (rs, rowNum) -> new Payment(
                rs.getLong("payment_id"),
                rs.getLong("user_id"),
                rs.getObject("order_id", Long.class),
                rs.getInt("amount"),
                rs.getString("payment_type"),
                rs.getTimestamp("created_at").toLocalDateTime()
        );
    }
}
