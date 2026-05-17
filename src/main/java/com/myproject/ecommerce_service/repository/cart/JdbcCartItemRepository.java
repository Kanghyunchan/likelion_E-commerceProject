package com.myproject.ecommerce_service.repository.cart;

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
public class JdbcCartItemRepository implements CartItemRepository{

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<CartItem> cartItemRowMapper = (rs, rowNum) -> new CartItem(
            rs.getLong("cart_id"),
            rs.getLong("user_id"),
            rs.getLong("product_id"),
            rs.getInt("quantity")
    );

    @Override
    public CartItem registration(CartItem cartItem) {
        String sql = "INSERT INTO cart_item (user_id, product_id, quantity) VALUES (?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"cart_id"});
            ps.setLong(1, cartItem.getUserId());
            ps.setLong(2, cartItem.getProductId());
            ps.setInt(3, cartItem.getQuantity());
            return ps;
        }, keyHolder);

        Long generatedId = keyHolder.getKey().longValue();
        return new CartItem(generatedId, cartItem.getUserId(), cartItem.getProductId(), cartItem.getQuantity());
    }

    @Override
    public Optional<CartItem> findById(Long cartId) {
        String sql = "SELECT * FROM cart_item WHERE cart_id = ?";
        List<CartItem> result = jdbcTemplate.query(sql, cartItemRowMapper, cartId);
        return result.stream().findAny();
    }

    @Override
    public List<CartItem> findByUserId(Long userId) {
        String sql = "SELECT * FROM cart_item WHERE user_id = ?";
        return jdbcTemplate.query(sql, cartItemRowMapper, userId);
    }

    @Override
    public Optional<CartItem> findByUserIdAndProductId(Long userId, Long productId) {
        String sql = "SELECT * FROM cart_item WHERE user_id = ? AND product_id = ?";
        List<CartItem> result = jdbcTemplate.query(sql, cartItemRowMapper, userId, productId);
        return result.stream().findAny();
    }

    @Override
    public void update(CartItem cartItem) {
        String sql = "UPDATE cart_item SET quantity = ? WHERE cart_id = ?";
        jdbcTemplate.update(sql, cartItem.getQuantity(), cartItem.getCartId());
    }

    @Override
    public void delete(Long cartId) {
        String sql = "DELETE FROM cart_item WHERE cart_id = ?";
        jdbcTemplate.update(sql, cartId);
    }

    @Override
    public void clearByUserId(Long userId) {
        String sql = "DELETE FROM cart_item WHERE user_id = ?";
        jdbcTemplate.update(sql, userId);
    }
}
