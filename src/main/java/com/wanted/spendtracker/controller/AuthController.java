package com.wanted.spendtracker.controller;

import com.wanted.spendtracker.domain.MemberAdapter;
import com.wanted.spendtracker.dto.request.TokenCreateRequest;
import com.wanted.spendtracker.dto.response.TokenCreateResponse;
import com.wanted.spendtracker.infrastructure.JwtTokenProvider;
import com.wanted.spendtracker.service.AuthService;
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
