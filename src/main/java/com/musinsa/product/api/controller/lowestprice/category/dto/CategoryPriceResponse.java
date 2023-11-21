package com.musinsa.product.api.controller.lowestprice.category.dto;


public record CategoryPriceResponse(
        String categoryName,
        CategoryPrice lowestPrice,
        CategoryPrice highestPrice
) {
    public static CategoryPriceResponse create(
            String categoryName,
            CategoryPrice lowestPrice,
            CategoryPrice highestPrice
    ) {
        return new CategoryPriceResponse(categoryName, lowestPrice, highestPrice);
    }

    public static CategoryPriceResponse empty(String categoryName)
    {
        return new CategoryPriceResponse(categoryName, null, null);
    }

    public static CategoryPrice create(String brandName, Long price)
    {
        return new CategoryPrice(brandName, price);
    }

    public record CategoryPrice(String brandName, Long price) {}
}
