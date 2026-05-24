package com.myproject.ecommerce_service.service.product;

import com.myproject.ecommerce_service.domain.product.Product;
import com.myproject.ecommerce_service.dto.product.ProductDetailResponse;
import com.myproject.ecommerce_service.dto.product.ProductListResponse;
import com.myproject.ecommerce_service.dto.product.ProductRegisterRequest;
import com.myproject.ecommerce_service.dto.product.ProductRegisterResponse;
import com.myproject.ecommerce_service.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    //상품 전체 목록 조회
    public List<ProductListResponse> getAllProducts(){
        return productRepository.findAll().stream()
                .map(ProductListResponse::new)
                .collect(Collectors.toList());
    }

    //상품 상세 조회
    public ProductDetailResponse getProductDetail(Long productId){
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다. (입력 ID: " +
                        productId + ")"));
        return new ProductDetailResponse(product);
    }

    //상품 등록
    @Transactional
    public ProductRegisterResponse registerProduct(ProductRegisterRequest request){
        Product product = new Product(
                null,
                request.getProductName(),
                request.getPrice(),
                request.getQuantity(),
                request.getDescription(),
                request.getImageUrl(),
                request.getStatus()
        );

        Product registerProduct = productRepository.registration(product);

        return new ProductRegisterResponse(
                registerProduct.getProductId(),
                registerProduct.getProductName(),
                registerProduct.getPrice(),
                registerProduct.getQuantity()
        );
    }

}
