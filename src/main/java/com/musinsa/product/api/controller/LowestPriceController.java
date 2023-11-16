package com.musinsa.product.api.controller;

import com.musinsa.product.api.controller.dto.BrandLowestPriceResponse;
import com.musinsa.product.api.controller.dto.CategoryLowestPriceResponse;
import com.musinsa.product.api.service.LowestPriceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/lowest")
public class LowestPriceController {

    private final LowestPriceService lowestPriceService;

    @GetMapping
    public CategoryLowestPriceResponse findCategoryLowestPriceBrand()
    {
        return lowestPriceService.findCategoryLowestPriceBrand();
    }

    @GetMapping("/brand")
    public BrandLowestPriceResponse findBrandLowestPrice()
    {
        return lowestPriceService.findBrandLowestPrice();
    }
}
