package com.musinsa.product.api.service.lowestprice.brand;

import com.musinsa.product.api.controller.lowestprice.brand.dto.LowestPriceBrandResponse;
import com.musinsa.product.api.controller.lowestprice.brand.dto.LowestPriceBrandResponse.BrandCategory;
import com.musinsa.product.config.IntegrationTestSupport;
import com.musinsa.product.domain.brand.Brand;
import com.musinsa.product.domain.brand.BrandRepository;
import com.musinsa.product.domain.type.CategoryType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

import static com.musinsa.product.global.config.redis.RedisKeyGenerator.BrandCategory;
import static com.musinsa.product.global.config.redis.RedisKeyGenerator.BrandTotalPrice;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

@DisplayName("브랜드 카테고리 최저가 검색 통합 테스트")
@Transactional
class BrandLowestPriceSearchServiceTest extends IntegrationTestSupport {

    @Autowired
    BrandLowestPriceSearchService brandLowestPriceSearchService;

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Autowired
    BrandRepository brandRepository;

    @BeforeEach
    public void setUp()
    {
        redisTemplate.execute(new RedisCallback<Object>() {
            public Object doInRedis(RedisConnection connection) {
                connection.flushAll();
                return null;
            }
        });
    }

    @DisplayName("단일 브랜드로 모든 카테고리 상품을 구매할 때 최저가격에 판매하는 브랜드와 카테고리의 상품가격, 총액을 조회할 수 있다.")
    @Test
    void brandCategorySearch()
    {
        // given
        Brand brand1 = brandRepository.save(Brand.create("BrandName1"));
        Brand brand2 = brandRepository.save(Brand.create("BrandName2"));

        Map<CategoryType, Long> cateMap1 = Map.of(
                CategoryType.TOP, 20L,
                CategoryType.OUTER, 20L,
                CategoryType.BOTTOM, 20L,
                CategoryType.SNEAKERS, 20L,
                CategoryType.BAG, 20L,
                CategoryType.HAT, 20L,
                CategoryType.SOCKS, 20L,
                CategoryType.ACCESSORY, 20L
        );

        Map<CategoryType, Long> cateMap2 = Map.of(
                CategoryType.TOP, 10L,
                CategoryType.OUTER, 10L,
                CategoryType.BOTTOM, 10L,
                CategoryType.SNEAKERS, 10L,
                CategoryType.BAG, 10L,
                CategoryType.HAT, 10L,
                CategoryType.SOCKS, 10L,
                CategoryType.ACCESSORY, 10L
        );

        Long sum1 = createBrandCategory(brand1.getBrandId(), cateMap1);
        Long sum2 = createBrandCategory(brand2.getBrandId(), cateMap2);

        // when
        LowestPriceBrandResponse response = brandLowestPriceSearchService.findLowestPriceBrand();

        // then
        assertThat(sum1).isGreaterThan(sum2);
        assert response != null;
        assertThat(response.brandName()).isNotNull();
        assertThat(response.brandName()).isEqualTo("BrandName2");
        assertThat(response.totalPrice()).isEqualTo(sum2);
        assertThat(response.categories()).extracting(BrandCategory::categoryName, BrandCategory::price)
                .contains(
                        tuple(CategoryType.TOP.getCategoryName(), 10L),
                        tuple(CategoryType.OUTER.getCategoryName(), 10L),
                        tuple(CategoryType.BOTTOM.getCategoryName(), 10L),
                        tuple(CategoryType.SNEAKERS.getCategoryName(), 10L),
                        tuple(CategoryType.BAG.getCategoryName(), 10L),
                        tuple(CategoryType.HAT.getCategoryName(), 10L),
                        tuple(CategoryType.SOCKS.getCategoryName(), 10L),
                        tuple(CategoryType.ACCESSORY.getCategoryName(), 10L)
                );
    }

    private Long createBrandCategory(Long brandId, Map<CategoryType, Long> cateMap)
    {
        for (Map.Entry<CategoryType, Long> entry : cateMap.entrySet())
        {
            redisTemplate.opsForZSet().add(BrandCategory(entry.getKey()), brandId.toString(), entry.getValue());
        }

        long sum = cateMap.values().stream().mapToLong((a) -> a).sum();
        redisTemplate.opsForZSet().add(BrandTotalPrice(), brandId.toString(), sum);
        return sum;
    }
}