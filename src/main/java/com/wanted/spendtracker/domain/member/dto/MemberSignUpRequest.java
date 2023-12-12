package com.wanted.spendtracker.domain.member.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberSignUpRequest {

    @Schema(description = "사용자 id", example = "test5678")
    @NotBlank(message = "MEMBER_ACCOUNT_NAME_BLANK")
    @Size(min = 2, max = 20, message = "MEMBER_ACCOUNT_NAME_LENGTH_INVALID")
    private String accountName;
    @Schema(description = "사용자 비밀번호", example = "qwer1234^^")
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
