package com.musinsa.product.api.controller.product;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.musinsa.product.api.controller.product.dto.request.ProductCreateRequest;
import com.musinsa.product.api.controller.product.dto.request.ProductUpdateRequest;
import com.musinsa.product.api.controller.product.dto.response.ProductUpdatedResponse;
import com.musinsa.product.api.service.product.ProductCreateService;
import com.musinsa.product.api.service.product.ProductDeleteService;
import com.musinsa.product.api.service.product.ProductUpdateService;
import com.musinsa.product.domain.type.CategoryType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("상품 컨트롤러 테스트")
@WebMvcTest(controllers = ProductController.class)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    ProductCreateService productCreateService;

    @MockBean
    ProductUpdateService productUpdateService;

    @MockBean
    ProductDeleteService productDeleteService;

    @Autowired
    ObjectMapper objectMapper;

    @DisplayName("생성 요청 - 빈 상품 이름")
    @Test
    void createProductEmptyProductName() throws Exception
    {
        ProductCreateRequest request = ProductCreateRequest.builder()
                .productName("")
                .price(100L)
                .categoryType(CategoryType.BAG)
                .brandId(1L)
                .build();

        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("상품 이름은 필수입니다."))
                .andExpect(jsonPath("$.status").value("400"));
    }

    @DisplayName("생성 요청 - 없는 BrandId")
    @Test
    void createProductEmptyBrandId() throws Exception
    {
        ProductCreateRequest request = ProductCreateRequest.builder()
                .productName("name")
                .price(100L)
                .categoryType(CategoryType.BAG)
                .brandId(null)
                .build();

        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("상품 브랜드 ID는 필수입니다."))
                .andExpect(jsonPath("$.status").value("400"));
    }

    @DisplayName("생성 요청 - 0원 미만 가격 요청")
    @Test
    void createProductUnderZeroPrice() throws Exception
    {
        ProductCreateRequest request = ProductCreateRequest.builder()
                .productName("name")
                .price(-100L)
                .categoryType(CategoryType.BAG)
                .brandId(1L)
                .build();

        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/api/product")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value("Bad Request"))
                .andExpect(jsonPath("$.message").value("가격은 0 이상이어야 합니다."))
                .andExpect(jsonPath("$.status").value("400"));
    }

    @DisplayName("생성 요청 - 없는 BrandId")
    @Test
    void updateProduct() throws Exception
    {
        ProductUpdateRequest request = ProductUpdateRequest.builder()
                .productName("product")
                .price(300L)
                .build();

        BDDMockito.given(productUpdateService.updateProduct(1L, request))
                        .willReturn(new ProductUpdatedResponse(
                                "product1",
                                1L,
                                1000L,
                                CategoryType.BAG.getCategoryName())
                        );
        // when
        mockMvc.perform(MockMvcRequestBuilders.put("/api/product/1")
                        .content(objectMapper.writeValueAsString(request))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productName").value("product1"))
                .andExpect(jsonPath("$.productId").value("1"))
                .andExpect(jsonPath("$.price").value("1000"))
                .andExpect(jsonPath("$.categoryType").value("가방"));
    }
}