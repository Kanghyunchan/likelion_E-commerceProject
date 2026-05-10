package com.myproject.ecommerce_service.controller;

import com.myproject.ecommerce_service.dto.CartItemRequest;
import com.myproject.ecommerce_service.dto.CartResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Cart", description = "장바구니 관련 API")
@RestController
@RequestMapping("/api/carts")
public class CartController {
    @Operation(summary = "장바구니 조회")
    @GetMapping
    public List<CartResponse> getCarts() {
        return List.of(new CartResponse(34L, "대파 키트", 5000));
    }

    @Operation(summary = "장바구니 상품 추가")
    @PostMapping("/items")
    public String addCartItem(@RequestBody CartItemRequest request) {
        return "장바구니에 상품이 추가되었습니다.";
    }

    @Operation(summary = "장바구니 상품 삭제")
    @DeleteMapping("/items/{cartItemId}")
    public void deleteCartItem(@PathVariable Long cartItemId) {

    }
}
