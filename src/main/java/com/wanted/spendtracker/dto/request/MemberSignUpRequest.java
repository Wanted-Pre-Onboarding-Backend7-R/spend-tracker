package com.wanted.spendtracker.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MemberSignUpRequest {

    @NotBlank
    private String accountName;

    @NotBlank
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
