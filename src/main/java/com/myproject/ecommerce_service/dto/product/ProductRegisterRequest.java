package com.myproject.ecommerce_service.dto.product;

import com.myproject.ecommerce_service.domain.product.ProductStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRegisterRequest {
    private String productName;
    private int price;
    private int quantity;
    private String description;
    private String imageUrl;
    private ProductStatus status;
}
