package com.myproject.ecommerce_service.repository.cart;

import com.myproject.ecommerce_service.domain.cart.CartItem;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository {
    CartItem registration(CartItem cartItem);
    Optional<CartItem> findById(Long cartItemId);
    List<CartItem> findByCartId(Long cartId);
    Optional<CartItem> findByCartIdAndProductId(Long cartId, Long productId); //중복 체크
    void update(CartItem cartItem); //수량 변경
    void delete(Long cartItemId); //장바구니 상품 제거

}
