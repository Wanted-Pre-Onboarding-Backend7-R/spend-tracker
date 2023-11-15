package com.wanted.spendtracker.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenCreateRequest {

    @NotBlank(message = "AUTH_ACCOUNT_NAME_BLANK")
    @Size(min = 2, max = 20, message = "AUTH_ACCOUNT_NAME_LENGTH_INVALID")
    private String accountName;

    @NotBlank(message = "AUTH_PASSWORD_BLANK")
    private String password;

}
