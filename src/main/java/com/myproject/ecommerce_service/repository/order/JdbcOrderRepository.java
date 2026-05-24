package com.myproject.ecommerce_service.repository.order;

import com.myproject.ecommerce_service.domain.order.OrderStatus;
import com.myproject.ecommerce_service.domain.order.Orders;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcOrderRepository implements OrderRepository{

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Orders> ordersRowMapper = (rs, rowNnm) -> new Orders(
            rs.getLong("order_id"),
            rs.getLong("user_id"),
            rs.getInt("total_price"),
            rs.getTimestamp("order_date").toLocalDateTime(),
            OrderStatus.valueOf(rs.getString("order_status")),
            rs.getString("shipping_address")
    );

    @Override
    public Orders registration(Orders orders) {
        String sql = "INSERT INTO orders (user_id, total_price, order_date, order_status, shipping_address) VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"order_id"});
            ps.setLong(1, orders.getUserId());
            ps.setInt(2, orders.getTotalPrice());
            ps.setTimestamp(3, Timestamp.valueOf(orders.getOrderDate()));
            ps.setString(4, orders.getOrderStatus().name());
            ps.setString(5, orders.getShippingAddress());
            return ps;
        }, keyHolder);

        Long generatedId = keyHolder.getKey().longValue();
        return new Orders(generatedId, orders.getUserId(), orders.getTotalPrice(), orders.getOrderDate(),
                orders.getOrderStatus(), orders.getShippingAddress());
    }

    @Override
    public Optional<Orders> findById(Long orderId) {
        String sql = "SELECT * FROM orders WHERE order_id = ?";
        List<Orders> result = jdbcTemplate.query(sql, ordersRowMapper, orderId);
        return result.stream().findAny();
    }

    @Override
    public void update(Orders orders) {
        String sql = "UPDATE orders SET order_status = ?, shipping_address = ? WHERE order_id = ?";
        jdbcTemplate.update(sql, orders.getOrderStatus().name(), orders.getShippingAddress(), orders.getOrderId());
    }

    @Override
    public void updateStatus(Orders orders) {
        String sql = "UPDATE orders SET order_status = ? WHERE order_id = ?";
        jdbcTemplate.update(sql, orders.getOrderStatus().name(), orders.getOrderId());
    }
}
