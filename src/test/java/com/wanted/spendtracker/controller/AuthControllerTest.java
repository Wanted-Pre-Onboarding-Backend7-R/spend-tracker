package com.wanted.spendtracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.spendtracker.domain.Member;
import com.wanted.spendtracker.dto.request.MemberSignUpRequest;
import com.wanted.spendtracker.dto.request.TokenCreateRequest;
import com.wanted.spendtracker.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static com.wanted.spendtracker.TestConstants.*;
import static com.wanted.spendtracker.exception.ErrorCode.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        MemberSignUpRequest memberSignUpRequest = MemberSignUpRequest.builder()
                .accountName(ACCOUNT_NAME)
                .password(PASSWORD)
                .build();
        memberRepository.save(Member.of(memberSignUpRequest, passwordEncoder.encode(memberSignUpRequest.getPassword())));
    }

    @Test
    @DisplayName("로그인 요청 시 jwt를 발급받는다.")
    void login() throws Exception {
        //given
        TokenCreateRequest tokenCreateRequest = TokenCreateRequest.builder()
                .accountName(ACCOUNT_NAME)
                .password(PASSWORD)
                .build();

        //when then
        mockMvc.perform(post("/api/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tokenCreateRequest)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.grantType").value("Bearer"))
                .andExpect(jsonPath("$.accessToken").exists())
                .andExpect(jsonPath("$.refreshToken").exists());
    }

    @Test
    @DisplayName("잘못된 계정명으로 로그인할 수 없다.")
    void login_invalidAccountName() throws Exception {
        //given
        TokenCreateRequest tokenCreateRequest = TokenCreateRequest.builder()
                .accountName(ACCOUNT_NAME + "t")
                .password(PASSWORD)
                .build();

        //when then
        mockMvc.perform(post("/api/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tokenCreateRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(AUTH_MEMBER_NOT_EXISTS.name()));
    }

    @Test
    @DisplayName("잘못된 비밀번호로 로그인할 수 없다.")
    void login_invalidPassword() throws Exception {
        //given
        TokenCreateRequest tokenCreateRequest = TokenCreateRequest.builder()
                .accountName(ACCOUNT_NAME)
                .password(PASSWORD + "t")
                .build();

        //when then
        mockMvc.perform(post("/api/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tokenCreateRequest)))
                .andDo(print())
                .andExpect(status().isUnauthorized())
                .andExpect(jsonPath("$.code").value(AUTH_AUTHENTICATION_FAILED.name()));
    }

    @Test
    @DisplayName("계정 이름이 공백이면 로그인할 수 없다.")
    void login_emptyAccountName() throws Exception {
        //given
        TokenCreateRequest tokenCreateRequest = TokenCreateRequest.builder()
                .accountName(BLANK)
                .password(PASSWORD)
                .build();

        //when then
        mockMvc.perform(post("/api/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tokenCreateRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(AUTH_ACCOUNT_NAME_BLANK.name()));
    }

    @Test
    @DisplayName("비밀번호가 공백이면 로그인할 수 없다.")
    void login_emptyPassword() throws Exception {
        //given
        TokenCreateRequest tokenCreateRequest = TokenCreateRequest.builder()
                .accountName(ACCOUNT_NAME)
                .password(BLANK)
                .build();

        //when then
        mockMvc.perform(post("/api/auth/login")
                        .contentType(APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(tokenCreateRequest)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.code").value(AUTH_PASSWORD_BLANK.name()));
    }

}
