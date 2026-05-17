package com.myproject.ecommerce_service.repository.product;

import com.myproject.ecommerce_service.domain.product.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Product registration(Product product); //product registration
    Optional<Product> findById(Long productId); //Find by Product ID
    List<Product> findAll(); // find all product
    void update(Product product); //update product
    void updateQuantity(Long productId, int updatedQuantity);
}
