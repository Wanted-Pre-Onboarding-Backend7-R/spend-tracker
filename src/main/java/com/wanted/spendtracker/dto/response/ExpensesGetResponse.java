package com.wanted.spendtracker.dto.response;

import com.wanted.spendtracker.domain.Expenses;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ExpensesGetResponse {

    private final Long memberId;

    private final Long categoryId;

    private final LocalDate date;

    private final Long amount;

    private final String memo;

    private final Boolean excludeFromTotalAmount;

    @Builder
    private ExpensesGetResponse(Long memberId, Long categoryId, LocalDate date, Long amount, String memo, Boolean excludeFromTotalAmount) {
        this.memberId = memberId;
        this.categoryId = categoryId;
        this.date = date;
        this.amount = amount;
        this.memo = memo;
        this.excludeFromTotalAmount = excludeFromTotalAmount;
    }

    public static ExpensesGetResponse from(Expenses expenses) {
        return ExpensesGetResponse.builder()
                .memberId(expenses.getMember().getId())
                .categoryId(expenses.getCategory().getId())
                .date(expenses.getDate())
                .amount(expenses.getAmount())
                .memo(expenses.getMemo())
                .excludeFromTotalAmount(expenses.getExcludeFromTotalAmount()).build();
    }

}