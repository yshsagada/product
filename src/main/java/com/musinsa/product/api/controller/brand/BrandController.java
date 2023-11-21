package com.musinsa.product.api.controller.brand;

import com.musinsa.product.api.controller.brand.dto.request.BrandCreateRequest;
import com.musinsa.product.api.controller.brand.dto.request.BrandUpdateRequest;
import com.musinsa.product.api.controller.brand.dto.response.BrandCreatedResponse;
import com.musinsa.product.api.controller.brand.dto.response.BrandResponse;
import com.musinsa.product.api.controller.brand.dto.response.BrandUpdatedResponse;
import com.musinsa.product.api.service.brand.BrandCreateService;
import com.musinsa.product.api.service.brand.BrandDeleteService;
import com.musinsa.product.api.service.brand.BrandService;
import com.musinsa.product.api.service.brand.BrandUpdateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/brand")
@RequiredArgsConstructor
@Tag(name = "브랜드 컨트롤러")
public class BrandController {

    private final BrandCreateService brandCreateService;
    private final BrandUpdateService brandUpdateService;
    private final BrandDeleteService brandDeleteService;
    private final BrandService brandService;

    @Operation(summary = "브랜드 생성")
    @PostMapping
    public ResponseEntity<BrandCreatedResponse> createBrand(@Valid @RequestBody BrandCreateRequest request)
    {
        return ResponseEntity.ok(brandCreateService.create(request));
    }

    @Operation(summary = "브랜드 변경")
    @PutMapping("/{brandId}")
    public ResponseEntity<BrandUpdatedResponse> updateBrand(
            @PathVariable Long brandId,
            @RequestBody BrandUpdateRequest request
    ) {
        return ResponseEntity.ok(brandUpdateService.update(brandId, request));
    }

    @Operation(summary = "브랜드 삭제")
    @DeleteMapping("/{brandId}")
    public void deleteBrand(@PathVariable Long brandId)
    {
        brandDeleteService.delete(brandId);
    }

    @Operation(summary = "테스트를 위한 현재 전체 브랜드 조회")
    @GetMapping("/all")
    public List<BrandResponse> findAllBrand()
    {
        return brandService.findAll();
    }
}
