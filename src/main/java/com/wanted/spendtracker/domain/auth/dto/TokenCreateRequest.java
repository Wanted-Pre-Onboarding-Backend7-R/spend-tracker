package com.wanted.spendtracker.domain.auth.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenCreateRequest {

    @Schema(description = "사용자 id", example = "test5678")
    @NotBlank(message = "AUTH_ACCOUNT_NAME_BLANK")
    @Size(min = 2, max = 20, message = "AUTH_ACCOUNT_NAME_LENGTH_INVALID")
    private String accountName;

    @Schema(description = "사용자 비밀번호", example = "qwer1234^^")
    @NotBlank(message = "AUTH_PASSWORD_BLANK")
    private String password;

}
