package com.musinsa.product.global.service.lowestprice.strategy.category.brand;

import com.musinsa.product.event.product.ProductTaskType;
import com.musinsa.product.global.service.kafka.payload.KafkaProductEvent;

public interface CategoryBrandLowestPriceStrategy {
    void process(KafkaProductEvent productEvent);
    ProductTaskType getProductTaskType();
}
