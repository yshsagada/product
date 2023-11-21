package com.musinsa.product.global.service.lowestprice.strategy.brand;

import com.musinsa.product.global.service.kafka.payload.KafkaBrandEvent;
import com.musinsa.product.event.brand.BrandTaskType;

public interface BrandLowestPriceStrategy {
    void process(KafkaBrandEvent kafkaBrandEvent);
    BrandTaskType getTaskType();
}
