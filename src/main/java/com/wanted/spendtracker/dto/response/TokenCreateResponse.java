package com.wanted.spendtracker.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@Getter
@Builder
@ToString
public class TokenCreateResponse {

    private String grantType;
    private String accessToken;
    private String refreshToken;

}
