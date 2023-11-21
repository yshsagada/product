package com.musinsa.product.exception;

import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Builder
public record ErrorResponse(String errorCode, String message, int status) {

    public static ResponseEntity<ErrorResponse> response(ErrorCode errorCode)
    {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(
                        ErrorResponse.builder()
                                .status(errorCode.getHttpStatus().value())
                                .message(errorCode.getMessage())
                                .errorCode(errorCode.getHttpStatus().getReasonPhrase())
                                .build()
                );
    }

    public static ResponseEntity<ErrorResponse> response(String message)
    {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        ErrorResponse.builder()
                                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .message(message)
                                .errorCode(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                                .build()
                );
    }

    public static ResponseEntity<ErrorResponse> badRequest(String message)
    {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(
                        ErrorResponse.builder()
                                .status(HttpStatus.BAD_REQUEST.value())
                                .message(message)
                                .errorCode(HttpStatus.BAD_REQUEST.getReasonPhrase())
                                .build()
                );
    }
}
