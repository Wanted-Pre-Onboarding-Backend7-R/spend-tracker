package com.wanted.spendtracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.spendtracker.domain.member.domain.Member;
import com.wanted.spendtracker.domain.member.dto.MemberSignUpRequest;
import com.wanted.spendtracker.global.exception.ErrorCode;
import com.wanted.spendtracker.domain.member.repository.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static com.wanted.spendtracker.TestConstants.ACCOUNT_NAME;
import static com.wanted.spendtracker.TestConstants.PASSWORD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("/api/members 통합 테스트")
@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class MemberControllerTest {

    private static final String URI = "/api/members";

    @Autowired
    private MockMvc mockMvc;

    @Autowired(required = false)
    private ObjectMapper mapper;

    @Autowired(required = false)
    private MemberRepository memberRepository;

    @Autowired(required = false)
    private PasswordEncoder passwordEncoder;

    @DisplayName("성공")
    @Test
    void signUpMember() throws Exception {
        MemberSignUpRequest memberSignUpRequest = MemberSignUpRequest.of(ACCOUNT_NAME, PASSWORD);
        String content = mapper.writeValueAsString(memberSignUpRequest);
        mockMvc.perform(post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").doesNotExist())
                .andDo(print())
                .andReturn();
    }

    @DisplayName("계정 이름 중복")
    @Test
    void signUpMember_accountNameDuplicated() throws Exception {
        //given
        Member member = Member.builder()
                .accountName(ACCOUNT_NAME)
                .password(passwordEncoder.encode(PASSWORD)).build();
        memberRepository.save(member);

        ErrorCode errorCode = ErrorCode.MEMBER_ACCOUNT_NAME_DUPLICATED;
        MemberSignUpRequest memberSignUpRequest = MemberSignUpRequest.of(ACCOUNT_NAME, PASSWORD);
        String content = mapper.writeValueAsString(memberSignUpRequest);

        //when, then
        mockMvc.perform(post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(errorCode.name()))
                .andExpect(jsonPath("$.message").value(errorCode.getMessage()))
                .andDo(print())
                .andReturn();
    }

}
