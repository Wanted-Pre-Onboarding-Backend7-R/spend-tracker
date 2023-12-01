package com.wanted.spendtracker.controller;

import com.wanted.spendtracker.domain.Member;
import com.wanted.spendtracker.domain.MemberAdapter;
import com.wanted.spendtracker.dto.response.ExpensesNotificationResponse;
import com.wanted.spendtracker.dto.response.ExpensesRecommendResponse;
import com.wanted.spendtracker.service.ExpensesConsultService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ExpensesConsultController {

    private final ExpensesConsultService expensesConsultService;

    @GetMapping("/api/expenses/recommend")
    public ResponseEntity<ExpensesRecommendResponse> recommendExpenses(@AuthenticationPrincipal MemberAdapter memberAdapter) {
        Member member = memberAdapter.getMember();
        ExpensesRecommendResponse expensesRecommendResponse = expensesConsultService.recommendExpenses(member);
        return ResponseEntity.ok().body(expensesRecommendResponse);
    }

    @GetMapping("/api/expenses/notify")
    public ResponseEntity<ExpensesNotificationResponse> notifyExpenses(@AuthenticationPrincipal MemberAdapter memberAdapter) {
        Member member = memberAdapter.getMember();
        ExpensesNotificationResponse expensesNotificationResponse = expensesConsultService.notifyExpenses(member);
        return ResponseEntity.ok().body(expensesNotificationResponse);
    }

}
