package com.musinsa.product.config.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.StandardCharsets;
import java.util.Objects;

public class CategoryLowestPriceSerializer implements RedisSerializer<CategoryLowestPrice> {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public byte[] serialize(CategoryLowestPrice value) throws SerializationException {
        if (Objects.isNull(value))
        {
            return null;
        }

        try{
            String json = MAPPER.writeValueAsString(value);
            return json.getBytes(StandardCharsets.UTF_8);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public CategoryLowestPrice deserialize(byte[] bytes) throws SerializationException {
        if (Objects.isNull(bytes))
        {
            return null;
        }

        try{
            return MAPPER.readValue(new String(bytes, StandardCharsets.UTF_8), CategoryLowestPrice.class);
        }catch (JsonProcessingException e)
        {
            throw new SerializationException("json deserialize ERROR", e);
        }
    }
}
