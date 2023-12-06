package com.wanted.spendtracker.domain.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberSignUpRequest {

    @NotBlank(message = "MEMBER_ACCOUNT_NAME_BLANK")
    @Size(min = 2, max = 20, message = "MEMBER_ACCOUNT_NAME_LENGTH_INVALID")
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
