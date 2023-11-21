package com.musinsa.product.global.service.lowestprice.strategy.category.brand;

import com.musinsa.product.event.product.ProductTaskType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CategoryBrandLowestPriceStrategyFactory {

    private final Map<ProductTaskType, CategoryBrandLowestPriceStrategy> strategies;

    @Autowired
    public CategoryBrandLowestPriceStrategyFactory(List<CategoryBrandLowestPriceStrategy> brandLowestPriceStrategies)
    {
        strategies = brandLowestPriceStrategies.stream()
                .collect(Collectors.toMap(CategoryBrandLowestPriceStrategy::getProductTaskType, strategy -> strategy));
    }

    public CategoryBrandLowestPriceStrategy get(ProductTaskType productTaskType)
    {
        return strategies.get(productTaskType);
    }
}
