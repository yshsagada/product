package com.musinsa.product.global.config.redis;

import com.musinsa.product.domain.type.CategoryType;

public class RedisKeyGenerator {
    private static final String CATEGORY_LOWEST_PRICE_KEY_PREFIX = "category:";
    private static final String BRAND_PRODUCT_LOWEST_PRICE_KEY_PREFIX = "brand:";
    private static final String BRAND_TOTAL_LOWEST_PRICE_KEY = "brand-total";

    public static String Category(CategoryType categoryType)
    {
        return CATEGORY_LOWEST_PRICE_KEY_PREFIX + categoryType.toString();
    }

    public static String BrandCategory(CategoryType categoryType)
    {
        return BRAND_PRODUCT_LOWEST_PRICE_KEY_PREFIX + categoryType.toString();
    }

    public static String BrandTotalPrice()
    {
        return BRAND_TOTAL_LOWEST_PRICE_KEY;
    }
}
