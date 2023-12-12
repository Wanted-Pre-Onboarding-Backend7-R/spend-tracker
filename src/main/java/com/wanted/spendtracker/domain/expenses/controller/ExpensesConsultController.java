package com.wanted.spendtracker.domain.expenses.controller;

import com.wanted.spendtracker.domain.expenses.dto.response.ExpensesRecommendResponse;
import com.wanted.spendtracker.domain.member.domain.Member;
import com.wanted.spendtracker.domain.auth.domain.MemberAdapter;
import com.wanted.spendtracker.domain.expenses.dto.response.ExpensesNotificationResponse;
import com.wanted.spendtracker.domain.expenses.service.ExpensesConsultService;
import com.wanted.spendtracker.global.docs.ExpensesConsultControllerDocs;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ExpensesConsultController implements ExpensesConsultControllerDocs {

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
