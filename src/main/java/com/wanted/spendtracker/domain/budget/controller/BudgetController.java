package com.wanted.spendtracker.domain.budget.controller;

import com.wanted.spendtracker.domain.auth.domain.MemberAdapter;
import com.wanted.spendtracker.domain.budget.service.BudgetService;
import com.wanted.spendtracker.domain.member.domain.Member;
import com.wanted.spendtracker.domain.budget.dto.request.BudgetRecommendRequest;
import com.wanted.spendtracker.domain.budget.dto.request.BudgetSetRequest;
import com.wanted.spendtracker.domain.budget.dto.response.BudgetRecommendResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;

    @PostMapping("/api/budgets")
    public ResponseEntity<Void> setBudget(@AuthenticationPrincipal MemberAdapter memberAdapter,
                                          @Valid @RequestBody BudgetSetRequest budgetSetRequest) {
        Member member = memberAdapter.getMember();
        budgetService.setBudget(member, budgetSetRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/api/budgets/recommend")
    public ResponseEntity<BudgetRecommendResponse> recommendBudget(@AuthenticationPrincipal MemberAdapter memberAdapter,
                                                                  @Valid @RequestBody BudgetRecommendRequest budgetRecommendRequest) {
        BudgetRecommendResponse budgetRecommendResponse = budgetService.recommendBudget(budgetRecommendRequest);
        return ResponseEntity.ok().body(budgetRecommendResponse);
    }

}
