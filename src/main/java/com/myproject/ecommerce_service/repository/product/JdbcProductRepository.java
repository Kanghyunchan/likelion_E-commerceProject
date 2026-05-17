package com.myproject.ecommerce_service.repository.product;

import com.myproject.ecommerce_service.domain.product.Product;
import com.myproject.ecommerce_service.domain.product.ProductStatus;
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
public class JdbcProductRepository implements ProductRepository{

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Product> productRowMapper = (rs, rowNum) -> new Product(
            rs.getLong("product_id"),
            rs.getString("product_name"),
            rs.getInt("price"),
            rs.getInt("quantity"),
            rs.getString("description"),
            rs.getString("imageURL"),
            ProductStatus.valueOf(rs.getString("status"))
    );


    @Override
    public Product registration(Product product) {
        String sql = "INSERT INTO product (product_name, price, quantity, description, imageURL, status) VALUES(?, ?, ?, ?, ?, ?)";
        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, new String[]{"product_id"});
            ps.setString(1, product.getProductName());
            ps.setInt(2, product.getPrice());
            ps.setInt(3, product.getQuantity());
            ps.setString(4, product.getDescription());
            ps.setString(5, product.getImageUrl());
            ps.setString(6, product.getStatus().name());
            return ps;
            }, keyHolder);
        Long generatedId = keyHolder.getKey().longValue();
        return new Product(
                generatedId,
                product.getProductName(),
                product.getPrice(),
                product.getQuantity(),
                product.getDescription(),
                product.getImageUrl(),
                product.getStatus()
        );
    }

    @Override
    public Optional<Product> findById(Long productId) {
        String sql = "SELECT * FROM product WHERE product_id = ?";
        List<Product> result = jdbcTemplate.query(sql, productRowMapper, productId);
        return result.stream().findAny();
    }

    @Override
    public List<Product> findAll() {
        String sql = "SELECT * FROM product";
        return jdbcTemplate.query(sql, productRowMapper);
    }

    @Override
    public void update(Product product) {
        String sql = "UPDATE product SET product_name = ?, price = ?, quantity = ?, description = ?, imageURL = ?, status =? WHERE product_id = ?";
        jdbcTemplate.update(sql,
                product.getProductName(),
                product.getPrice(),
                product.getQuantity(),
                product.getDescription(),
                product.getImageUrl(),
                product.getStatus().name(),
                product.getProductId()
        );
    }
}
