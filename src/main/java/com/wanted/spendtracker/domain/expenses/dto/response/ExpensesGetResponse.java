package com.wanted.spendtracker.domain.expenses.dto.response;

import com.wanted.spendtracker.domain.expenses.domain.Expenses;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
public class ExpensesGetResponse {

    private final Long categoryId;

    private final LocalDate date;

    private final Long amount;

    private final String memo;

    private final Boolean excludeFromTotalAmount;

    @Builder
    private ExpensesGetResponse(Long categoryId, LocalDate date, Long amount, String memo, Boolean excludeFromTotalAmount) {
        this.categoryId = categoryId;
        this.date = date;
        this.amount = amount;
        this.memo = memo;
        this.excludeFromTotalAmount = excludeFromTotalAmount;
    }

    public static ExpensesGetResponse from(Expenses expenses) {
        return ExpensesGetResponse.builder()
                .categoryId(expenses.getCategory().getId())
                .date(expenses.getDate())
                .amount(expenses.getAmount())
                .memo(expenses.getMemo())
                .excludeFromTotalAmount(expenses.getExcludeFromTotalAmount()).build();
    }

}
