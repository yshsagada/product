package com.musinsa.product.global.config.kafka.consumer;

import com.musinsa.product.global.service.kafka.payload.KafkaBrandEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.Map;

@Configuration
public class BrandEventConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootStrapServers;

    @Bean(name = "brandEventConsumerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, KafkaBrandEvent> brandEventConsumerContainerFactory()
    {
        ConcurrentKafkaListenerContainerFactory<String, KafkaBrandEvent> factory
                = new ConcurrentKafkaListenerContainerFactory<>();

        factory.getContainerProperties().setAckMode(ContainerProperties.AckMode.MANUAL_IMMEDIATE);
        factory.setConsumerFactory(brandEventConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, KafkaBrandEvent> brandEventConsumerFactory()
    {
        JsonDeserializer<KafkaBrandEvent> deserializer = new JsonDeserializer<>(KafkaBrandEvent.class);
        deserializer.addTrustedPackages("*");
        deserializer.setRemoveTypeHeaders(false);
        deserializer.setUseTypeMapperForKey(true);

        ErrorHandlingDeserializer<KafkaBrandEvent> errorHandlingDeserializer
                = new ErrorHandlingDeserializer<>(deserializer);

        Map<String, Object> props = Map.of(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapServers,
                ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class,
                ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class,
                ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false",
                ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest"
        );

        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), errorHandlingDeserializer);
    }
}
