package com.wanted.spendtracker.domain.expenses.controller;

import com.wanted.spendtracker.domain.auth.domain.MemberAdapter;
import com.wanted.spendtracker.domain.expenses.dto.request.ExpensesCreateRequest;
import com.wanted.spendtracker.domain.expenses.dto.request.ExpensesGetListRequest;
import com.wanted.spendtracker.domain.expenses.dto.request.ExpensesUpdateRequest;
import com.wanted.spendtracker.domain.expenses.dto.response.ExpensesGetListResponse;
import com.wanted.spendtracker.domain.expenses.dto.response.ExpensesGetResponse;
import com.wanted.spendtracker.domain.expenses.service.ExpensesService;
import com.wanted.spendtracker.domain.member.domain.Member;
import com.wanted.spendtracker.global.docs.ExpenseControllerDocs;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
public class ExpensesController implements ExpenseControllerDocs {

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
    public ResponseEntity<ExpensesGetResponse> getExpenses(@AuthenticationPrincipal MemberAdapter memberAdapter,
                                                           @PathVariable Long expensesId) {
        Member member = memberAdapter.getMember();
        ExpensesGetResponse expensesResponse = expensesService.getExpenses(member, expensesId);
        return ResponseEntity.ok().body(expensesResponse);
    }

    @GetMapping ("/api/expenses")
    public ResponseEntity<ExpensesGetListResponse> getExpensesList(@AuthenticationPrincipal MemberAdapter memberAdapter,
                                                                   @Valid @RequestBody ExpensesGetListRequest expensesGetRequest,
                                                                   @PageableDefault Pageable pageable) {
        Member member = memberAdapter.getMember();
        ExpensesGetListResponse expensesList = expensesService.getExpensesList(member, expensesGetRequest, pageable);
        return ResponseEntity.ok().body(expensesList);
    }

    @DeleteMapping ("/api/expenses/{expensesId}")
    public ResponseEntity<Void> deleteExpenses(@AuthenticationPrincipal MemberAdapter memberAdapter,
                                               @PathVariable Long expensesId) {
        Member member = memberAdapter.getMember();
        expensesService.deleteExpenses(member, expensesId);
        return ResponseEntity.ok().build();
    }

}
