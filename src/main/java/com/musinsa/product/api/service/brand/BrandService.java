package com.musinsa.product.api.service.brand;

import com.musinsa.product.api.controller.brand.dto.response.BrandResponse;
import com.musinsa.product.domain.brand.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BrandService {
    private final BrandRepository brandRepository;

    public List<BrandResponse> findAll()
    {
        return brandRepository.findAll().stream()
                .map(BrandResponse::of)
                .collect(Collectors.toList());
    }
}
