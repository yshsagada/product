package com.musinsa.product.api.controller.product;

import com.musinsa.product.api.controller.product.dto.request.ProductCreateRequest;
import com.musinsa.product.api.controller.product.dto.request.ProductUpdateRequest;
import com.musinsa.product.api.controller.product.dto.response.ProductCreatedResponse;
import com.musinsa.product.api.controller.product.dto.response.ProductUpdatedResponse;
import com.musinsa.product.api.service.product.ProductCreateService;
import com.musinsa.product.api.service.product.ProductDeleteService;
import com.musinsa.product.api.service.product.ProductUpdateService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/product")
public class ProductController {

    private final ProductCreateService productCreateService;
    private final ProductUpdateService productUpdateService;
    private final ProductDeleteService productDeleteService;

    @PostMapping
    public ResponseEntity<ProductCreatedResponse> createProduct(@Valid @RequestBody ProductCreateRequest request)
    {
        return ResponseEntity.ok(productCreateService.createProduct(request));
    }

    @PutMapping("/{productId}")
    public ResponseEntity<ProductUpdatedResponse> updateProduct(
            @PathVariable Long productId,
            @Valid @RequestBody ProductUpdateRequest request
    ) {
        return ResponseEntity.ok(productUpdateService.updateProduct(productId, request));
    }

    @DeleteMapping("/{productId}")
    public void deleteProduct(@PathVariable Long productId)
    {
        productDeleteService.deleteProduct(productId);
    }
}
