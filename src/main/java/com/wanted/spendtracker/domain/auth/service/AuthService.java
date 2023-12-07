package com.wanted.spendtracker.domain.auth.service;

import com.wanted.spendtracker.domain.member.domain.Member;
import com.wanted.spendtracker.domain.auth.domain.MemberAdapter;
import com.wanted.spendtracker.domain.auth.dto.TokenCreateRequest;
import com.wanted.spendtracker.domain.auth.dto.TokenCreateResponse;
import com.wanted.spendtracker.domain.auth.infrastructure.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, String> redisTemplate;

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

    @Transactional
    public void logout(MemberAdapter memberAdapter, String accessToken) {
        Long expiration = jwtTokenProvider.getExpiration(accessToken);
        String accountName = memberAdapter.getUsername();
        if (redisTemplate.opsForValue().get(accountName) != null) {
            redisTemplate.delete(accountName);
        }
        redisTemplate.opsForValue().set(accessToken, "logout", Duration.ofMillis(expiration));
    }

}
