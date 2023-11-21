package com.musinsa.product.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    NOT_FOUND_PRODUCT(HttpStatus.INTERNAL_SERVER_ERROR, "없는 상품입니다."),
    NOT_FOUND_BRAND(HttpStatus.INTERNAL_SERVER_ERROR, "없는 브랜드입니다."),

    INVALID_PARAMETER(HttpStatus.INTERNAL_SERVER_ERROR, "parameter 에러"),
    SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "SERVER ERROR"),
    ;

    ErrorCode(HttpStatus httpStatus, String message)
    {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    private final HttpStatus httpStatus;
    private final String message;
}
