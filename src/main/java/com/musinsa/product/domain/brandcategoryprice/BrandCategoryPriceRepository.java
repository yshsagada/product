package com.musinsa.product.domain.brandcategoryprice;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BrandCategoryPriceRepository extends JpaRepository<BrandCategoryPrice, Long> {
    @Query("select bcp from BrandCategoryPrice bcp order by bcp.totalPrice limit 1")
    BrandCategoryPrice findLowestTotalPrice();
}
