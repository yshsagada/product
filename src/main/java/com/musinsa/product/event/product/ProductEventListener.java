package com.musinsa.product.event.product;

import com.musinsa.product.global.service.kafka.ProducerService;
import com.musinsa.product.global.service.kafka.payload.KafkaProductEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@RequiredArgsConstructor
@Component
public class ProductEventListener {

    private final ProducerService producerService;

    @Async
    @TransactionalEventListener
    public void handle(ProductEvent productEvent)
    {
        KafkaProductEvent kafkaProductEvent = KafkaProductEvent.builder()
                .productId(productEvent.productId())
                .productPrice(productEvent.productPrice())
                .productTaskType(productEvent.productTaskType())
                .brandId(productEvent.brandId())
                .brandName(productEvent.brandName())
                .categoryType(productEvent.categoryType())
                .build();

        producerService.producer(kafkaProductEvent);
    }
}
