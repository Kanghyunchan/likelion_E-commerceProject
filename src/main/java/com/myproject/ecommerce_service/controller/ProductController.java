package com.myproject.ecommerce_service.controller;
import com.myproject.ecommerce_service.dto.ProductResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "Product", description = "상품 관련 API")
@RestController
@RequestMapping("api/products")
public class ProductController {
    @Operation(summary = "상품 목록 조회")
    @GetMapping
    public List<ProductResponse> getProducts(){
        return List.of(new ProductResponse(1L, "우유", 7000));
    }

    @Operation(summary = "상품 상세 조회")
    @GetMapping("/{productId}")
    public ProductResponse getProduct(@PathVariable Long productId){
        return new ProductResponse(productId, "상세 제품", 13030);
    }

    @Operation(summary = "인기 상품 조회")
    @GetMapping("/popular")
    public List<ProductResponse> getPopularProducts() {
        return List.of(new ProductResponse(3L, "인기 제품", 160200));
    }
}
