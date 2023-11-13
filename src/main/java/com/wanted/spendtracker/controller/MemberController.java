package com.wanted.spendtracker.controller;

import com.wanted.spendtracker.dto.request.MemberSignUpRequest;
import com.wanted.spendtracker.service.MemberService;
import com.wanted.spendtracker.util.PasswordValidator;
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
        Long memberId = memberService.signup(memberSignUpRequest);
        return ResponseEntity.created(URI.create("api/members/" + memberId)).build();
    }

}
