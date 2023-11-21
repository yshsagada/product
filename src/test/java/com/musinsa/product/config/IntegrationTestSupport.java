package com.musinsa.product.config;

import org.junit.ClassRule;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.DockerComposeContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.io.File;
import java.time.Duration;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-integration.yml")
@ActiveProfiles("integration")
@Testcontainers
public class IntegrationTestSupport {

    @ClassRule
    public static DockerComposeContainer<?> dockerComposeContainer = new DockerComposeContainer<>(
            new File("src/test/resources/docker-compose.yml"))
            .withExposedService("redis_1", 6379,
                    Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(20)))
            .withExposedService("zookeeper_1", 2181,
                    Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(30)))
            .withExposedService("kafka_1", 9092,
                    Wait.forListeningPort().withStartupTimeout(Duration.ofSeconds(30)))
            ;

    static
    {
        dockerComposeContainer.start();
    }
}
