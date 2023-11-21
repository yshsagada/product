package com.musinsa.product.api.controller.lowestprice.category;

import com.musinsa.product.api.controller.lowestprice.category.dto.CategoryLowestPriceResponse;
import com.musinsa.product.api.controller.lowestprice.category.dto.CategoryPriceResponse;
import com.musinsa.product.api.service.lowestprice.category.CategoryLowestPriceSearchService;
import com.musinsa.product.domain.type.CategoryType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "카테고리 최저가 검색 컨트롤러")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/lowest-price")
public class CategoryLowestPriceSearchController {

    private final CategoryLowestPriceSearchService categoryLowestPriceSearchService;

    @Operation(summary = "카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회하는 API")
    @GetMapping("/categories")
    public CategoryLowestPriceResponse findAllCategoriesLowestPrice()
    {
        return categoryLowestPriceSearchService.findAllCategoriesLowestPrice();
    }

    @Operation(summary = "카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회하는 API")
    @GetMapping("/category")
    public CategoryPriceResponse findLowestPriceInCategory(@RequestParam CategoryType type)
    {
        return categoryLowestPriceSearchService.findLowestPriceInCategory(type);
    }
}
