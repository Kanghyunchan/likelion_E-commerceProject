package com.myproject.ecommerce_service.service.cart;

import com.myproject.ecommerce_service.domain.cart.Cart;
import com.myproject.ecommerce_service.domain.cart.CartItem;
import com.myproject.ecommerce_service.domain.product.Product;
import com.myproject.ecommerce_service.dto.cart.CartAddRequest;
import com.myproject.ecommerce_service.dto.cart.CartItemResponse;
import com.myproject.ecommerce_service.repository.cart.CartItemRepository;
import com.myproject.ecommerce_service.repository.cart.CartRepository;
import com.myproject.ecommerce_service.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;

    //장바구니 상품 추가
    @Transactional
    public void addCart(CartAddRequest request){
        Cart cart = cartRepository.findByUserId(request.getUserId())
                .orElseGet(() -> cartRepository.registration(new Cart(null, request.getUserId())));

        cartItemRepository.findByCartIdAndProductId(cart.getCartId(), request.getProductId())
                .ifPresentOrElse(
                        existingItem -> {
                            existingItem.addQuantity(request.getQuantity());
                            cartItemRepository.update(existingItem);
                        },
                        () -> {
                            CartItem cartItem = new CartItem(null, cart.getCartId(), request.getProductId(), request.getQuantity());
                            cartItemRepository.registration(cartItem);
                        }
                );
    }

    //장바구니 전체 조회
    public List<CartItemResponse> getCartList(Long userId){
        Optional<Cart> cartOptional = cartRepository.findByUserId(userId);
        if(cartOptional.isEmpty()) return List.of();

        Cart cart = cartOptional.get();

        List<CartItem> cartItems = cartItemRepository.findByCartId(cart.getCartId());
        return cartItems.stream().map(item -> {
            Product product = productRepository.findById(item.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
            return new CartItemResponse(
                    item.getCartItemId(),
                    item.getProductId(),
                    product.getProductName(),
                    product.getPrice(),
                    item.getQuantity()
            );
        }).collect(Collectors.toList());
    }

    //장바구니 수량 변경
    @Transactional
    public void updateCartQuantity(Long cartItemId, int quantity){
        if (quantity <= 0) {
            throw new IllegalArgumentException("수량은 1개 이상이어야 합니다.");
        }

        CartItem cartItem = cartItemRepository.findById(cartItemId)
                .orElseThrow(() -> new IllegalArgumentException("장바구니 항목이 존재하지 않습니다. ID: " + cartItemId));
        CartItem updatedItem = new CartItem(
                cartItem.getCartItemId(),
                cartItem.getCartId(),
                cartItem.getProductId(),
                quantity
        );
        cartItemRepository.update(updatedItem);
    }

    @Transactional
    public void deleteCartItem(Long cartItemId){
        cartItemRepository.findById(cartItemId)
                        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 장바구니 항목입니다. ID: " + cartItemId));
        cartItemRepository.delete(cartItemId);
    }
}
