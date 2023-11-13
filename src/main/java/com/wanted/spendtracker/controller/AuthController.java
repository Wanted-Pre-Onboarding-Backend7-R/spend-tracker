package com.wanted.spendtracker.controller;

import com.wanted.spendtracker.dto.request.TokenCreateRequest;
import com.wanted.spendtracker.dto.response.TokenCreateResponse;
import com.wanted.spendtracker.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/api/auth/login")
    public ResponseEntity<TokenCreateResponse> login(@RequestBody @Valid TokenCreateRequest tokenCreateRequest) {
        TokenCreateResponse tokenCreateResponse = authService.login(tokenCreateRequest);
        return ResponseEntity.ok().body(tokenCreateResponse);
    }

}
