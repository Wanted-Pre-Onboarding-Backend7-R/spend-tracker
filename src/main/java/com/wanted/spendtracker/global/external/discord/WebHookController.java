package com.wanted.spendtracker.global.external.discord;

import com.wanted.spendtracker.domain.auth.domain.MemberAdapter;
import com.wanted.spendtracker.domain.member.domain.Member;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Discord WebHook")
public class WebHookController {

    private final WebHookService webHookService;

    @PostMapping("/api/webhook/expenses/recommend")
    public ResponseEntity<Void> webHookRecommendExpenses(@AuthenticationPrincipal MemberAdapter memberAdapter) {
        Member member = memberAdapter.getMember();
        webHookService.webHookRecommendExpenses(member);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/api/webhook/expenses/notify")
    public ResponseEntity<Void> webhookNotifyExpenses(@AuthenticationPrincipal MemberAdapter memberAdapter) {
        Member member = memberAdapter.getMember();
        webHookService.webHookNotifyExpenses(member);
        return ResponseEntity.ok().build();
    }

}
