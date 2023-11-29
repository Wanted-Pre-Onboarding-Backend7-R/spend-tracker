package com.wanted.spendtracker.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ExpensesRecommendResponse {

    private final Long totalAvailableExpenses;
    private final List<CategoryAmountResponse> AvailableExpensesByCategory;

    @Builder
    private ExpensesRecommendResponse(Long totalAvailableExpenses, List<CategoryAmountResponse> availableExpensesByCategory) {
        this.totalAvailableExpenses = totalAvailableExpenses;
        AvailableExpensesByCategory = availableExpensesByCategory;
    }

    public static ExpensesRecommendResponse of(Long totalAvailableExpenses, List<CategoryAmountResponse> availableExpensesByCategory) {
        return ExpensesRecommendResponse.builder()
                .totalAvailableExpenses(totalAvailableExpenses)
                .availableExpensesByCategory(availableExpensesByCategory).build();
    }

}
