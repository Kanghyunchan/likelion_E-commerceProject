package com.myproject.ecommerce_service.repository.cart;

import com.myproject.ecommerce_service.domain.cart.Cart;
import com.myproject.ecommerce_service.domain.cart.CartItem;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcCartRepository implements CartRepository{
    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Cart> cartRowMapper = (rs, rowNum) -> new Cart(
            rs.getLong("cart_id"),
            rs.getLong("user_id")
    );

    @Override
    public Cart registration(Cart cart) {
        String sql = "INSERT INTO cart (user_id) VALUES (?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"cart_id"});
            ps.setLong(1, cart.getUserId());
            return ps;
        }, keyHolder);
        return new Cart(keyHolder.getKey().longValue(), cart.getUserId());
    }

    @Override
    public Optional<Cart> findByUserId(Long userId) {
        String sql = "SELECT * FROM cart WHERE user_id = ?";
        List<Cart> result = jdbcTemplate.query(sql, cartRowMapper, userId);
        return result.stream().findFirst();
    }
    @Override
    public void clearByUserId(Long userId) {
        String sql = "DELETE FROM cart WHERE user_id = ?";
        jdbcTemplate.update(sql, userId);
    }
}
