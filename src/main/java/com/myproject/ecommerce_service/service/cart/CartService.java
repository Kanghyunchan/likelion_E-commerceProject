package com.myproject.ecommerce_service.service.cart;

import com.myproject.ecommerce_service.domain.cart.CartItem;
import com.myproject.ecommerce_service.domain.product.Product;
import com.myproject.ecommerce_service.dto.cart.CartAddRequest;
import com.myproject.ecommerce_service.dto.cart.CartItemResponse;
import com.myproject.ecommerce_service.repository.cart.CartItemRepository;
import com.myproject.ecommerce_service.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    //장바구니 상품 추가
    @Transactional
    public void addCart(CartAddRequest request){
        cartItemRepository.findByUserIdAndProductId(request.getUserId(), request.getProductId())
                .ifPresentOrElse(
                        existingItem -> {existingItem.addQuantity(request.getQuantity());
                            cartItemRepository.update(existingItem);
                        }, () -> {
                            CartItem cartItem = new CartItem(null, request.getUserId(), request.getProductId(), request.getQuantity());
                            cartItemRepository.registration(cartItem);
                        }
                );
    }

    //장바구니 전체 조회
    public List<CartItemResponse> getCartList(Long userId){
        List<CartItem> cartItems = cartItemRepository.findByUserId(userId);

        return cartItems.stream()
                .map(item -> {
                    Product product = productRepository.findById(item.getProductId())
                            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다. ID: " + item.getProductId()));
                return new CartItemResponse(
                        item.getCartId(),
                        item.getProductId(),
                        product.getProductName(),
                        product.getPrice(),
                        item.getQuantity()
                );
                })
                .collect(Collectors.toList());

    }

    //장바구니 수량 변경
    @Transactional
    public void updateCartQuantity(Long cartId, int quantity){
        if (quantity <= 0) {
            throw new IllegalArgumentException("수량은 1개 이상이어야 합니다.");
        }

        CartItem cartItem = cartItemRepository.findById(cartId)
                .orElseThrow(() -> new IllegalArgumentException("장바구니 항목이 존재하지 않습니다. ID: " + cartId));
        CartItem updatedItem = new CartItem(
                cartItem.getCartId(),
                cartItem.getUserId(),
                cartItem.getProductId(),
                quantity
        );
        cartItemRepository.update(updatedItem);
    }

    @Transactional
    public void deleteCartItem(Long cartId){
        cartItemRepository.findById(cartId)
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 장바구니 항목입니다. ID: " + cartId));
        cartItemRepository.delete(cartId);
    }
}
