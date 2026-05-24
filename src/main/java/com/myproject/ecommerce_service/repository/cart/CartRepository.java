package com.myproject.ecommerce_service.repository.cart;

import com.myproject.ecommerce_service.domain.cart.Cart;

import java.util.Optional;

public interface CartRepository {
    Cart registration(Cart cart);
    Optional<Cart> findByUserId(Long userId);
    void clearByUserId(Long userId); //장바구니 비우기
}
