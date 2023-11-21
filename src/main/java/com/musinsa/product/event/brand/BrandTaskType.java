package com.musinsa.product.event.brand;

import lombok.Getter;

@Getter
public enum BrandTaskType {
    UPDATE("변경"),
    CREATE("생성"),
    DELETE("삭제"),
    ;

    private final String taskName;

    BrandTaskType(String taskName)
    {
        this.taskName = taskName;
    }
}
