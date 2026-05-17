package com.myproject.ecommerce_service.dto.product;

import com.myproject.ecommerce_service.domain.product.Product;
import com.myproject.ecommerce_service.domain.product.ProductStatus;
import lombok.Getter;

@Getter
public class ProductDetailResponse {
    private final Long productId;
    private final String productName;
    private final int price;
    private final int quantity;
    private final String description;
    private final String imageUrl;
    private final ProductStatus status;

    public ProductDetailResponse(Product product){
        this.productId = product.getProductId();
        this.productName = product.getProductName();
        this.price = product.getPrice();
        this.quantity = product.getQuantity();
        this.description = product.getDescription();
        this.imageUrl = product.getImageUrl();
        this.status = product.getStatus();
    }
}
