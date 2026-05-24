package com.myproject.ecommerce_service.controller.cart;

import com.myproject.ecommerce_service.domain.cart.CartItem;
import com.myproject.ecommerce_service.dto.cart.CartAddRequest;
import com.myproject.ecommerce_service.dto.cart.CartItemResponse;
import com.myproject.ecommerce_service.dto.cart.CartUpdateQuantityRequest;
import com.myproject.ecommerce_service.service.cart.CartService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Cart", description = "장바구니 관련 API")
@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @Operation(summary = "장바구니 상품 추가", description = "장바구니에 상품을 담습니다. 이미 존재할 경우 수량이 증가합니다.")
    @PostMapping
    public ResponseEntity<Void> addCart(@RequestBody CartAddRequest request){
        cartService.addCart(request);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Operation(summary = "장바구니 조회", description = "특정 유저의 장바구니 품목 리스트를 조회합니다.")
    @GetMapping
    public ResponseEntity<List<CartItemResponse>> getCartList(@RequestParam Long userId){
        List<CartItemResponse> responses = cartService.getCartList(userId);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "장바구니 수량 변경", description = "장바구니에 담긴 특정 아이템 수량을 변경합니다.")
    @PatchMapping("/{cartItemId}/quantity")
    public ResponseEntity<Void> updateCartQuantity(@PathVariable Long cartItemId, @RequestBody CartUpdateQuantityRequest request){
        cartService.updateCartQuantity(cartItemId, request.getQuantity());
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "장바구니 상품 삭제", description = "장바구니에서 특정 상품을 제거합니다.")
    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<Void> deleteCartItem(@PathVariable Long cartItemId){
        cartService.deleteCartItem(cartItemId);
        return ResponseEntity.ok().build();
    }
}
