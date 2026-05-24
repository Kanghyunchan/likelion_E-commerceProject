package com.myproject.ecommerce_service.controller.product;

import com.myproject.ecommerce_service.dto.product.ProductDetailResponse;
import com.myproject.ecommerce_service.dto.product.ProductListResponse;
import com.myproject.ecommerce_service.dto.product.ProductRegisterRequest;
import com.myproject.ecommerce_service.dto.product.ProductRegisterResponse;
import com.myproject.ecommerce_service.service.product.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Product", description = "상품 조회 관련 API")
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @Operation(summary = "상품 전체 목록 조회", description = "메인 화면에 노출되는 상품 리스트를 전부 조회합니다.")
    @GetMapping
    public ResponseEntity<List<ProductListResponse>> getAllProducts() {
        List<ProductListResponse> products = productService.getAllProducts();
        return ResponseEntity.ok(products);
    }

    @Operation(summary = "상품 상세 정보 조회", description = "특정 상품의 전체 정보를 조회합니다.")
    @GetMapping("/{productId}")
    public ResponseEntity<ProductDetailResponse> getProductDetail(@PathVariable Long productId) {
        ProductDetailResponse productDetail = productService.getProductDetail(productId);
        return ResponseEntity.ok(productDetail);
    }

    @Operation(summary = "상품 등록", description = "새로운 상품 정보를 입력받아 DB에 등록합니다.")
    @PostMapping
    public ResponseEntity<ProductRegisterResponse> registerProduct(@RequestBody ProductRegisterRequest request){
        ProductRegisterResponse response = productService.registerProduct(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
