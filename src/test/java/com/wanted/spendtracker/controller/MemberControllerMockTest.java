package com.wanted.spendtracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.spendtracker.config.SecurityConfig;
import com.wanted.spendtracker.dto.request.MemberSignUpRequest;
import com.wanted.spendtracker.exception.ErrorCode;
import com.wanted.spendtracker.service.MemberService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static com.wanted.spendtracker.TestConstants.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@DisplayName("/api/members WebMvc")
@Import(SecurityConfig.class)
@WebMvcTest(MemberController.class)
public class MemberControllerMockTest {


    private static final String URI = "/api/members";

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private MemberService memberService;
    @Autowired
    private ObjectMapper mapper;

    @BeforeEach
    void setUp() {
        given(memberService.signUp(any(MemberSignUpRequest.class)))
                .willReturn(1L);
    }

    @DisplayName("성공")
    @Test
    void signUpMember() throws Exception {
        MemberSignUpRequest memberSignUpRequest = MemberSignUpRequest.of(ACCOUNT_NAME, PASSWORD);
        String content = mapper.writeValueAsString(memberSignUpRequest);
        mockMvc.perform(post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").doesNotExist())
                .andDo(print())
                .andReturn();
    }

    @DisplayName("[계정 이름] 공백")
    @Test
    void signUpMember_accountNameBlank() throws Exception {
        MemberSignUpRequest memberSignUpRequest = MemberSignUpRequest.of(BLANK, PASSWORD);
        String content = mapper.writeValueAsString(memberSignUpRequest);
        ErrorCode errorCode = ErrorCode.MEMBER_ACCOUNT_NAME_BLANK;
        mockMvc.perform(post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value(errorCode.name()))
                .andExpect(jsonPath("$.message").value(errorCode.getMessage()))
                .andDo(print())
                .andReturn();
    }

    @DisplayName("[비밀번호] 공백")
    @Test
    void signUpMember_passwordBlank() throws Exception {
        MemberSignUpRequest memberSignUpRequest = MemberSignUpRequest.of(ACCOUNT_NAME, BLANK);
        String content = mapper.writeValueAsString(memberSignUpRequest);
        ErrorCode errorCode = ErrorCode.MEMBER_PASSWORD_BLANK;
        mockMvc.perform(post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value(errorCode.name()))
                .andExpect(jsonPath("$.message").value(errorCode.getMessage()))
                .andDo(print())
                .andReturn();
    }

    @DisplayName("[비밀번호] 짧음")
    @ValueSource(strings = {"101sadf", "345^12as", "zxc_123^^"})
    @ParameterizedTest
    void signUpMember_passwordShort(String password) throws Exception {
        MemberSignUpRequest memberSignUpRequest = MemberSignUpRequest.of(ACCOUNT_NAME, password);
        String content = mapper.writeValueAsString(memberSignUpRequest);
        ErrorCode errorCode = ErrorCode.MEMBER_PASSWORD_SHORT;
        mockMvc.perform(post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value(errorCode.name()))
                .andExpect(jsonPath("$.message").value(errorCode.getMessage()))
                .andDo(print())
                .andReturn();
    }

    @DisplayName("[비밀번호] 3개 이상 같은 문자 반복")
    @ValueSource(strings = {"asdfj3___123", "asdf^###axx", "Asxcvttt90^@"})
    @ParameterizedTest
    void signUpMember_passwordRepeated(String password) throws Exception {
        MemberSignUpRequest memberSignUpRequest = MemberSignUpRequest.of(ACCOUNT_NAME, password);
        String content = mapper.writeValueAsString(memberSignUpRequest);
        ErrorCode errorCode = ErrorCode.MEMBER_PASSWORD_REPEATED;
        mockMvc.perform(post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value(errorCode.name()))
                .andExpect(jsonPath("$.message").value(errorCode.getMessage()))
                .andDo(print())
                .andReturn();
    }

    @DisplayName("[비밀번호] 단순 숫자/영문/특수문자만 (2개 이상 미조합)")
    @ValueSource(strings = {"1238471912398", "aasdfkjhea", "##@@!##!!@!!^"})
    @ParameterizedTest
    void signUpMember_passwordSimple(String password) throws Exception {
        MemberSignUpRequest memberSignUpRequest = MemberSignUpRequest.of(ACCOUNT_NAME, password);
        String content = mapper.writeValueAsString(memberSignUpRequest);
        ErrorCode errorCode = ErrorCode.MEMBER_PASSWORD_SIMPLE;
        mockMvc.perform(post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value(errorCode.name()))
                .andExpect(jsonPath("$.message").value(errorCode.getMessage()))
                .andDo(print())
                .andReturn();
    }

    @DisplayName("[비밀번호] 개인정보 포함")
    @ValueSource(strings = {ACCOUNT_NAME + "^^"})
    @ParameterizedTest
    void signUpMember_passwordIncludedPersonalInfo(String password) throws Exception {
        MemberSignUpRequest memberSignUpRequest = MemberSignUpRequest.of(ACCOUNT_NAME, password);
        String content = mapper.writeValueAsString(memberSignUpRequest);
        ErrorCode errorCode = ErrorCode.MEMBER_PASSWORD_PERSONAL_INFO;
        mockMvc.perform(post(URI)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(content)
                )
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errorCode").value(errorCode.name()))
                .andExpect(jsonPath("$.message").value(errorCode.getMessage()))
                .andDo(print())
                .andReturn();
    }

}
