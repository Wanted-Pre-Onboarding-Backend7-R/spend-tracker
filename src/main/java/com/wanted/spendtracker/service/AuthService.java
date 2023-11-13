package com.wanted.spendtracker.service;

import com.wanted.spendtracker.domain.Member;
import com.wanted.spendtracker.domain.MemberAdapter;
import com.wanted.spendtracker.dto.request.TokenCreateRequest;
import com.wanted.spendtracker.dto.response.TokenCreateResponse;
import com.wanted.spendtracker.infrastructure.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional(readOnly = true)
    public TokenCreateResponse login(TokenCreateRequest tokenCreateRequest) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                tokenCreateRequest.getAccountName(),
                tokenCreateRequest.getPassword()
        );
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        Member member = ((MemberAdapter) authentication.getPrincipal()).getMember();
        return jwtTokenProvider.generateToken(member);
    }
}
