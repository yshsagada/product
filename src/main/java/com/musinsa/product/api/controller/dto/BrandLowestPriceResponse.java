package com.musinsa.product.api.controller.dto;

import com.musinsa.product.domain.category.CategoryType;
import com.musinsa.product.domain.brandcategoryprice.BrandCategoryPrice;

import java.util.List;

public record BrandLowestPriceResponse(
        String brandName,
        Long totalPrice,
        List<BrandCategory> categories
) {
    public static BrandLowestPriceResponse of(BrandCategoryPrice lowestTotalPrice)
    {
        return new BrandLowestPriceResponse(
                lowestTotalPrice.getBrandName(),
                lowestTotalPrice.getTotalPrice(),
                List.of(
                        new BrandCategory(CategoryType.TOP.getCategoryName(), lowestTotalPrice.getTopPrice()),
                        new BrandCategory(CategoryType.OUTER.getCategoryName(), lowestTotalPrice.getOuterPrice()),
                        new BrandCategory(CategoryType.BOTTOM.getCategoryName(), lowestTotalPrice.getBottomPrice()),
                        new BrandCategory(CategoryType.SNEAKERS.getCategoryName(), lowestTotalPrice.getSneakersPrice()),
                        new BrandCategory(CategoryType.BAG.getCategoryName(), lowestTotalPrice.getBagPrice()),
                        new BrandCategory(CategoryType.HAT.getCategoryName(), lowestTotalPrice.getHatPrice()),
                        new BrandCategory(CategoryType.SOCKS.getCategoryName(), lowestTotalPrice.getSocksPrice())
                )
        );
    }

    public record BrandCategory(
            String categoryName,
            Long price
    ) {}
}
