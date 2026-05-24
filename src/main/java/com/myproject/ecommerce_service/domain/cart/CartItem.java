package com.myproject.ecommerce_service.domain.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CartItem {
    private Long cartItemId;
    private Long cartId;
    private Long productId;
    private int quantity;

    public void decreaseQuantity(int quantity){
        if(this.quantity < quantity)
            throw new IllegalArgumentException("장바구니에 담긴 수량보다 많이 줄일 수 없습니다. (현재 수량: " + this.quantity + ")");
        this.quantity -= quantity;
    }

    public void addQuantity(int quantity){
        if(quantity < 0)
            throw new IllegalArgumentException("추가할 수량이 0보다 커야 합니다.");
        this.quantity += quantity;
    }

    public void updateQuantity(int quantity){
        if(quantity <= 0)
            throw new IllegalArgumentException("수량은 1개 이상이어야 합니다.");
        this.quantity = quantity;
    }
}
