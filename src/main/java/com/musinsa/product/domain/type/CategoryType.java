package com.musinsa.product.domain.type;

import lombok.Getter;

@Getter
public enum CategoryType {

    TOP("상의"),
    OUTER("아우터"),
    BOTTOM("바지"),
    SNEAKERS("스니커즈"),
    BAG("가방"),
    HAT("모자"),
    SOCKS("양말"),
    ACCESSORY("액세서리"),
    ;

    private final String categoryName;

    CategoryType(String categoryName) {
        this.categoryName = categoryName;
    }
}
