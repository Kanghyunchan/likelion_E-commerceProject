package com.myproject.ecommerce_service.dto.product;

import com.myproject.ecommerce_service.domain.product.Product;
import lombok.Getter;

@Getter
public class ProductListResponse {
    private final Long productId;
    private final String productName;
    private final int price;
    private final String imageUrl;

    public ProductListResponse(Product product){
        this.productId = product.getProductId();
        this.productName = product.getProductName();
        this.price = product.getPrice();
        this.imageUrl = product.getImageUrl();
    }
}
