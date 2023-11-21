package com.musinsa.product.api.service.lowestprice.category;

import com.musinsa.product.api.controller.lowestprice.category.dto.CategoryLowestPriceResponse;
import com.musinsa.product.api.controller.lowestprice.category.dto.CategoryPriceResponse;
import com.musinsa.product.config.IntegrationTestSupport;
import com.musinsa.product.domain.brand.Brand;
import com.musinsa.product.domain.brand.BrandRepository;
import com.musinsa.product.domain.product.Product;
import com.musinsa.product.domain.product.ProductRepository;
import com.musinsa.product.domain.type.CategoryType;
import com.musinsa.product.factory.ProductMockFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.musinsa.product.global.config.redis.RedisKeyGenerator.Category;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.groups.Tuple.tuple;

@DisplayName("카테고리 최저가 검색 통합 테스트")
@Transactional
class CategoryLowestPriceSearchServiceTest extends IntegrationTestSupport {

    @Autowired
    CategoryLowestPriceSearchService categoryLowestPriceSearchService;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @BeforeEach
    public void setUp()
    {
        redisTemplate.execute(new RedisCallback<Object>()
        {
            public Object doInRedis(RedisConnection connection)
            {
                connection.flushAll();
                return null;
            }
        });
    }

    @DisplayName("카테고리 이름으로 최저, 최고 가격 브랜드와 상품 가격을 조회할 수 있다.")
    @Test
    void findLowestPriceInCategory()
    {
        // given
        CategoryType givenCategoryType = CategoryType.BAG;
        Brand brand1 = Brand.create("brand1");
        Brand brand2 = Brand.create("brand2");

        brandRepository.saveAll(List.of(brand1, brand2));

        Product product1 = ProductMockFactory.create().brandId(brand1.getBrandId()).productPrice(50L).build();
        Product product2 = ProductMockFactory.create().brandId(brand2.getBrandId()).productPrice(200L).build();
        Product product3 = ProductMockFactory.create().brandId(brand2.getBrandId()).productPrice(100L).build();

        productRepository.saveAll(List.of(product1, product2, product3));

        redisTemplate.opsForZSet().add(Category(CategoryType.BAG), product1.getProductId().toString(), product1.getProductPrice());
        redisTemplate.opsForZSet().add(Category(CategoryType.BAG), product2.getProductId().toString(), product2.getProductPrice());
        redisTemplate.opsForZSet().add(Category(CategoryType.BAG), product3.getProductId().toString(), product3.getProductPrice());

        // when
        CategoryPriceResponse lowestPriceInCategory = categoryLowestPriceSearchService.findLowestPriceInCategory(
                givenCategoryType
        );

        // then
        assertThat(lowestPriceInCategory.categoryName()).isEqualTo(CategoryType.BAG.getCategoryName());
        assertThat(lowestPriceInCategory.lowestPrice().price()).isEqualTo(50L);
        assertThat(lowestPriceInCategory.highestPrice().price()).isEqualTo(200L);
    }

    @DisplayName("카테고리 별 최저가격 브랜드와 상품 가격, 총액을 조회할 수 있다.")
    @Test
    void findAllCategoriesLowestPrice()
    {
        // given
        Brand brand1 = Brand.create("brand1");
        Brand brand2 = Brand.create("brand2");

        brandRepository.saveAllAndFlush(List.of(brand1, brand2));

        Product product1 = createProduct(CategoryType.TOP, 5L, brand1.getBrandId());
        Product product2 = createProduct(CategoryType.OUTER, 5L, brand1.getBrandId());
        Product product3 = createProduct(CategoryType.BOTTOM, 5L, brand1.getBrandId());
        Product product4 = createProduct(CategoryType.SNEAKERS, 5L, brand1.getBrandId());
        Product product5 = createProduct(CategoryType.BAG, 5L, brand2.getBrandId());
        Product product6 = createProduct(CategoryType.HAT, 5L, brand2.getBrandId());
        Product product7 = createProduct(CategoryType.SOCKS, 5L, brand2.getBrandId());
        Product product8 = createProduct(CategoryType.ACCESSORY, 5L, brand2.getBrandId());

        // 중복 카테고리 넣어서 확인
        Product product9 = createProduct(CategoryType.TOP, 100L, brand1.getBrandId());
        Product product10 = createProduct(CategoryType.OUTER, 100L, brand2.getBrandId());

        productRepository.saveAll(
                List.of(
                    product1,
                    product2,
                    product3,
                    product4,
                    product5,
                    product6,
                    product7,
                    product8,
                    product9,
                    product10
                )
        );

        add(product1);
        add(product2);
        add(product3);
        add(product4);
        add(product5);
        add(product6);
        add(product7);
        add(product8);
        add(product9);
        add(product1);

        CategoryLowestPriceResponse response = categoryLowestPriceSearchService.findAllCategoriesLowestPrice();

        Set<String> responseCategoryNames = categoryLowestPriceSearchService.findAllCategoriesLowestPrice()
                .categoryLowestPrices()
                .stream()
                .map(CategoryLowestPriceResponse.CategoryLowestPrice::categoryName)
                .collect(Collectors.toSet());

        Set<String> categoryTypeNames = Arrays.asList(CategoryType.values()).stream()
                .map(CategoryType::getCategoryName).collect(Collectors.toSet());

        assertThat(responseCategoryNames).containsAll(categoryTypeNames);
        assertThat(response.totalPrice()).isEqualTo(40L);
        assertThat(response.categoryLowestPrices())
                .extracting(
                        CategoryLowestPriceResponse.CategoryLowestPrice::categoryName,
                        CategoryLowestPriceResponse.CategoryLowestPrice::brandName,
                        CategoryLowestPriceResponse.CategoryLowestPrice::price
                )
                .contains(
                        tuple(CategoryType.TOP.getCategoryName(), brand1.getBrandName(), 5L),
                        tuple(CategoryType.OUTER.getCategoryName(), brand1.getBrandName(), 5L),
                        tuple(CategoryType.BOTTOM.getCategoryName(), brand1.getBrandName(), 5L),
                        tuple(CategoryType.SNEAKERS.getCategoryName(), brand1.getBrandName(), 5L),
                        tuple(CategoryType.BAG.getCategoryName(), brand2.getBrandName(), 5L),
                        tuple(CategoryType.HAT.getCategoryName(), brand2.getBrandName(), 5L),
                        tuple(CategoryType.SOCKS.getCategoryName(), brand2.getBrandName(), 5L),
                        tuple(CategoryType.ACCESSORY.getCategoryName(), brand2.getBrandName(), 5L)
                );
    }

    private void add(Product product)
    {
        redisTemplate.opsForZSet().add(
                Category(product.getCategoryType()),
                product.getProductId().toString(),
                product.getProductPrice()
        );
    }

    private Product createProduct(CategoryType type, Long price, Long brandId)
    {
        return ProductMockFactory.create()
                .categoryType(type)
                .productPrice(price)
                .brandId(brandId)
                .build();
    }
}