package com.wanted.spendtracker.controller;

import com.wanted.spendtracker.domain.Member;
import com.wanted.spendtracker.domain.MemberAdapter;
import com.wanted.spendtracker.dto.request.ExpensesCreateRequest;
import com.wanted.spendtracker.dto.request.ExpensesUpdateRequest;
import com.wanted.spendtracker.dto.response.ExpensesResponse;
import com.wanted.spendtracker.service.ExpensesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class ExpensesController {

    private final ExpensesService expensesService;

    @PostMapping("/api/expenses")
    public ResponseEntity<Void> createExpenses(@AuthenticationPrincipal MemberAdapter memberAdapter,
                                               @Valid @RequestBody ExpensesCreateRequest expensesCreateRequest) {
        Member member = memberAdapter.getMember();
        Long expensesId = expensesService.createExpenses(member, expensesCreateRequest);
        return ResponseEntity.created(URI.create("/api/expenses/" + expensesId)).build();
    }

    @PatchMapping ("/api/expenses/{expensesId}")
    public ResponseEntity<Void> updateExpenses(@AuthenticationPrincipal MemberAdapter memberAdapter,
                                                           @PathVariable Long expensesId,
                                                           @Valid @RequestBody ExpensesUpdateRequest expensesUpdateRequest) {
        Member member = memberAdapter.getMember();
        expensesService.updateExpenses(member, expensesId, expensesUpdateRequest);
        return ResponseEntity.ok().build();
    }

    @GetMapping ("/api/expenses/{expensesId}")
    public ResponseEntity<ExpensesResponse> getExpenses(@AuthenticationPrincipal MemberAdapter memberAdapter,
                                                        @PathVariable Long expensesId) {
        Member member = memberAdapter.getMember();
        ExpensesResponse expensesResponse = expensesService.getExpenses(member, expensesId);
        return ResponseEntity.ok().body(expensesResponse);
    }

    @DeleteMapping ("/api/expenses/{expensesId}")
    public ResponseEntity<Void> deleteExpenses(@AuthenticationPrincipal MemberAdapter memberAdapter,
                                                        @PathVariable Long expensesId) {
        Member member = memberAdapter.getMember();
        expensesService.deleteExpenses(member, expensesId);
        return ResponseEntity.ok().build();
    }

}
