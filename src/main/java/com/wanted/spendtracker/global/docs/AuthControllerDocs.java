package com.wanted.spendtracker.global.docs;

import com.wanted.spendtracker.domain.auth.domain.MemberAdapter;
import com.wanted.spendtracker.domain.auth.dto.TokenCreateRequest;
import com.wanted.spendtracker.domain.auth.dto.TokenCreateResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

@Tag(name = "인증")
public interface AuthControllerDocs {

    @Operation(summary = "사용자 로그인 API", description = "사용자가 로그인을 하고 토큰을 발급 받는다.")
    @ApiResponse(responseCode = "200", description = "OK")
    public ResponseEntity<TokenCreateResponse> login(TokenCreateRequest tokenCreateRequest);

    @Operation(summary = "사용자 로그아웃 API", description = "사용자가 로그아웃을 하고 토큰을 삭제한다.")
    @ApiResponse(responseCode = "200", description = "OK")
    public ResponseEntity<Void> logout(MemberAdapter memberAdapter, HttpServletRequest request);

}
