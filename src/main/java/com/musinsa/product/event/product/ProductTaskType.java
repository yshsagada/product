package com.musinsa.product.event.product;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductTaskType {
    CREATE("생성"),
    DELETE("삭제"),
    UPDATE("변경"),
    ;

    private final String task;
}
