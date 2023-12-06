package com.wanted.spendtracker.domain.auth.controller;

import com.wanted.spendtracker.domain.auth.domain.MemberAdapter;
import com.wanted.spendtracker.domain.auth.dto.TokenCreateRequest;
import com.wanted.spendtracker.domain.auth.dto.TokenCreateResponse;
import com.wanted.spendtracker.domain.auth.infrastructure.JwtTokenProvider;
import com.wanted.spendtracker.domain.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/api/auth/login")
    public ResponseEntity<TokenCreateResponse> login(@RequestBody @Valid TokenCreateRequest tokenCreateRequest) {
        TokenCreateResponse tokenCreateResponse = authService.login(tokenCreateRequest);
        return ResponseEntity.ok().body(tokenCreateResponse);
    }

    @PostMapping("/api/auth/logout")
    public ResponseEntity<Void> logout(@AuthenticationPrincipal MemberAdapter memberAdapter, HttpServletRequest request) {
        String accessToken = jwtTokenProvider.resolveToken(request);
        authService.logout(memberAdapter, accessToken);
        return ResponseEntity.ok().build();
    }

}
