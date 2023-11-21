package com.musinsa.product.global.service.lowestprice.strategy.category;

import com.musinsa.product.event.product.ProductTaskType;
import com.musinsa.product.global.service.kafka.payload.KafkaProductEvent;

public interface CategoryLowestPriceRedisStrategy {
    void process(KafkaProductEvent productEvent);
    ProductTaskType getProductTaskType();
}
