package com.musinsa.product.api.controller.lowestprice.brand;

import com.musinsa.product.api.controller.lowestprice.brand.dto.LowestPriceBrandResponse;
import com.musinsa.product.api.service.lowestprice.brand.BrandLowestPriceSearchService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@Tag(name = "카테고리 최저가 브랜드 검색 컨트롤러")
@RequestMapping("/api/brand/search")
public class BrandLowestPriceSearchController {

    private final BrandLowestPriceSearchService brandLowestPriceSearchService;

    @Operation(summary = "단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회하는 API")
    @GetMapping("/lowest-price")
    public LowestPriceBrandResponse findLowestPriceBrand()
    {
        return brandLowestPriceSearchService.findLowestPriceBrand();
    }
}
