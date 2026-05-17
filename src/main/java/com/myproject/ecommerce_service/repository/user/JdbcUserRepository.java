package com.myproject.ecommerce_service.repository.user;

import com.myproject.ecommerce_service.domain.user.User;
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
public class JdbcUserRepository implements UserRepository{

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<User> userRowMapper = (rs, rowNum) -> new User(
            rs.getLong("user_id"),
            rs.getString("user_name"),
            rs.getString("email"),
            rs.getString("password"),
            rs.getString("phone_number"),
            rs.getString("address")
    );

    @Override
    public User registration(User user) {
        String sql = "INSERT INTO users (user_name, email, password, phone_number, address) VALUES (?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"user_id"});
            ps.setString(1, user.getUserName());
            ps.setString(2, user.getEmail());
            ps.setString(3, user.getPassword());
            ps.setString(4, user.getPhoneNumber());
            ps.setString(5, user.getAddress());
            return ps;
        }, keyHolder);
        Long generatedId = keyHolder.getKey().longValue();

        return new User(
                generatedId,
                user.getUserName(),
                user.getEmail(),
                user.getPassword(),
                user.getPhoneNumber(),
                user.getAddress()
        );
    }

    @Override
    public Optional<User> findById(Long userId) {
        String sql = "SELECT * FROM users WHERE user_id = ?";
        List<User> result = jdbcTemplate.query(sql, userRowMapper, userId);
        return result.stream().findAny();
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        List<User> result = jdbcTemplate.query(sql, userRowMapper, email);
        return result.stream().findAny();
    }

    @Override
    public void update(User user) {
        String sql = "UPDATE users SET user_name = ?, password = ?, phone_number =?, address = ? WHERE user_id = ?";
        jdbcTemplate.update(sql,
                user.getUserName(),
                user.getPassword(),
                user.getPhoneNumber(),
                user.getAddress(),
                user.getUserId()
                );
    }
}
