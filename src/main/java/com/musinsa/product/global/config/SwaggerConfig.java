package com.musinsa.product.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "무신사 상품 최저가 서비스",
                description = "무신사 상품 최저가 서비스",
                version = "v1.0"))
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi chatOpenApi() {
        String[] paths = {"/api/**"};

        return GroupedOpenApi.builder()
                .group("무신사")
                .pathsToMatch(paths)
                .build();
    }
}