package com.myproject.ecommerce_service.repository.order;

import com.myproject.ecommerce_service.domain.order.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class JdbcOrderItemRepository implements OrderItemRepository{

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<OrderItem> orderItemRowMapper = (rs, rowNum) -> new OrderItem(
            rs.getLong("order_item_id"),
            rs.getLong("order_id"),
            rs.getLong("product_id"),
            rs.getInt("quantity"),
            rs.getInt("saleprice")
    );

    @Override
    public OrderItem registration(OrderItem orderItem) {
        String sql = "INSERT INTO order_item (order_id, product_id, quantity, saleprice) VALUES (?, ?, ?, ?)";
        jdbcTemplate.update(sql,
                orderItem.getOrderId(),
                orderItem.getProductId(),
                orderItem.getQuantity(),
                orderItem.getSalePrice()
        );
        return orderItem;
    }

    @Override
    public List<OrderItem> findByOrderId(Long orderId) {
        String sql = "SELECT * FROM order_item WHERE order_id = ?";
        return jdbcTemplate.query(sql, orderItemRowMapper, orderId);
    }
}
