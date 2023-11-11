package com.wanted.spendtracker.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberSignUpRequest {

    @NotBlank(message = "MEMBER_ACCOUNT_NAME_BLANK")
    @Min(value = 2, message = "MEMBER_ACCOUNT_NAME_SHORT")
    @Max(value = 20, message = "MEMBER_ACCOUNT_NAME_LONG")
    private String accountName;

    @NotBlank(message = "MEMBER_PASSWORD_BLANK")
    private String password;

    @Builder
    private MemberSignUpRequest(String accountName, String password) {
        this.accountName = accountName;
        this.password = password;
    }

    public static MemberSignUpRequest of(String accountName, String password) {
        return MemberSignUpRequest.builder()
                .accountName(accountName)
                .password(password).build();
    }

}