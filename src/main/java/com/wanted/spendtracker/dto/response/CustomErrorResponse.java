package com.wanted.spendtracker.dto.response;

import com.wanted.spendtracker.exception.ErrorCode;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CustomErrorResponse {

    private final String errorCode;
    private final String message;

    @Builder
    private CustomErrorResponse(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public static CustomErrorResponse of(ErrorCode errorCode) {
        return CustomErrorResponse.builder()
                .errorCode(errorCode.name())
                .message(errorCode.getMessage())
                .build();
    }

}
