package com.wanted.spendtracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wanted.spendtracker.dto.request.MemberSignUpRequest;
import com.wanted.spendtracker.exception.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static com.wanted.spendtracker.TestConstants.ACCOUNT_NAME;
import static com.wanted.spendtracker.TestConstants.PASSWORD;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("/api/members 통합 테스트")
@AutoConfigureMockMvc
@SpringBootTest
class MemberControllerTest {

    private static final String URI = "/api/members";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;


    @DisplayName("사용자 회원가입")
    @Nested
    class Signup {

        @DisplayName("성공")
        @Test
        void givenValidInfo_then201() throws Exception {
            doJoin().andExpect(status().isCreated())
                    .andExpect(jsonPath("$").doesNotExist())
                    .andDo(print())
                    .andReturn();
        }

        @DisplayName("계정 이름 중복")
        @Test
        void givenDuplicateAccountName_then400() throws Exception {
            ErrorCode errorCode = ErrorCode.MEMBER_ACCOUNT_NAME_DUPLICATED;
            doJoin().andExpect(status().isBadRequest())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.errorCode").value(errorCode.name()))
                    .andExpect(jsonPath("$.message").value(errorCode.getMessage()))
                    .andDo(print())
                    .andReturn();
        }

        private ResultActions doJoin() throws Exception {
            MemberSignUpRequest memberSignUpRequest = MemberSignUpRequest.of(ACCOUNT_NAME, PASSWORD);
            String content = mapper.writeValueAsString(memberSignUpRequest);
            return mockMvc.perform(post(URI)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(content));
        }

    }

}
