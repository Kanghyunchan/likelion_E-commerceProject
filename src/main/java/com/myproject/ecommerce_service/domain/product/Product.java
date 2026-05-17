package com.myproject.ecommerce_service.domain.product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private Long productId;
    private String productName;
    private int price;
    private int quantity;
    private String description;
    private String imageUrl;
    private ProductStatus status;

    public void decreaseQuantity(int quantity){
        if(this.quantity < quantity)
            throw new IllegalArgumentException("재고가 부족합니다. (현재 재고: " + this.quantity + ")");
        this.quantity -= quantity;
    }

    public void addQuantity(int quantity){
        if(quantity < 0)
            throw new IllegalArgumentException("추가할 수량이 0보다 커야 합니다.");
        this.quantity += quantity;
    }

    public void changePrice(int price){
        if(price < 0)
            throw new IllegalArgumentException("변경할 금액이 0원 이상이어야 합니다.");
        this.price = price;
    }

    public void updateInfo(String productName, String description, String imageUrl){
        this.productName = productName;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    public void soldOut(){ this.status = ProductStatus.SOLD_OUT; }
    public void sell(){
        this.status = ProductStatus.SELL;
    }
    public void hide(){
        this.status = ProductStatus.HIDDEN;
    }
}
