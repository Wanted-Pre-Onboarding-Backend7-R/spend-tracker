package com.wanted.spendtracker.controller;

import com.wanted.spendtracker.domain.MemberAdapter;
import com.wanted.spendtracker.dto.request.BudgetSetRequest;
import com.wanted.spendtracker.service.BudgetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BudgetController {

    private final BudgetService budgetService;

    @PostMapping("/api/budgets")
    public ResponseEntity<Void> setBudget(@AuthenticationPrincipal MemberAdapter memberAdapter, @Valid @RequestBody BudgetSetRequest budgetSetRequest) {
        budgetService.setBudget(memberAdapter, budgetSetRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

}
