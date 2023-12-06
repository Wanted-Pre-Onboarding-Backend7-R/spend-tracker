package com.wanted.spendtracker.member.controller;

import com.wanted.spendtracker.member.dto.MemberSignUpRequest;
import com.wanted.spendtracker.member.service.MemberService;
import com.wanted.spendtracker.validation.PasswordValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @PostMapping("api/members")
    public ResponseEntity<Void> signUp(@Valid @RequestBody MemberSignUpRequest memberSignUpRequest) {
        PasswordValidator.validatePassword(memberSignUpRequest);
        Long memberId = memberService.signUp(memberSignUpRequest);
        return ResponseEntity.created(URI.create("api/members/" + memberId)).build();
    }

}
