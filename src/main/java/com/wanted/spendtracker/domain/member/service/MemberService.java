package com.wanted.spendtracker.domain.member.service;

import com.wanted.spendtracker.domain.member.domain.Member;
import com.wanted.spendtracker.domain.member.dto.MemberSignUpRequest;
import com.wanted.spendtracker.domain.member.repository.MemberRepository;
import com.wanted.spendtracker.global.exception.CustomException;
import com.wanted.spendtracker.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public Long signUp(MemberSignUpRequest memberSignUpRequest) {
        Member member = createMember(memberSignUpRequest);
        try {
            memberRepository.save(member);
            return member.getId();
        } catch (DataIntegrityViolationException e) {
            throw new CustomException(ErrorCode.MEMBER_ACCOUNT_NAME_DUPLICATED, e);
        }
    }

    public Member createMember(MemberSignUpRequest memberSignUpRequest) {
        String encodedPassword = passwordEncoder.encode(memberSignUpRequest.getPassword());
        return Member.of(memberSignUpRequest, encodedPassword);
    }

}
