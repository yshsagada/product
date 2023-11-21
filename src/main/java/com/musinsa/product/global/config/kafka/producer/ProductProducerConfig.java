package com.musinsa.product.global.config.kafka.producer;

import com.musinsa.product.global.service.kafka.payload.KafkaProductEvent;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.Map;

@Configuration
public class ProductProducerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootStrapServers;

    @Bean
    public ProducerFactory<String, KafkaProductEvent> producerFactory()
    {
        Map<String, Object> configProps = Map.of(
                ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServers,
                ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class,
                ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class
        );
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean(name = "productEventTemplate")
    public KafkaTemplate<String, KafkaProductEvent> productEventKafkaTemplate()
    {
        return new KafkaTemplate<>(producerFactory());
    }
}
