package com.wanted.spendtracker.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ExpensesRecommendResponse {

    private final Long totalAvailableExpenses;
    private final List<CategoryAmountResponse> availableExpensesByCategoryList;

    @Builder
    private ExpensesRecommendResponse(Long totalAvailableExpenses, List<CategoryAmountResponse> availableExpensesByCategoryList) {
        this.totalAvailableExpenses = totalAvailableExpenses;
        this.availableExpensesByCategoryList = availableExpensesByCategoryList;
    }

    public static ExpensesRecommendResponse of(Long totalAvailableExpenses, List<CategoryAmountResponse> availableExpensesByCategoryList) {
        return ExpensesRecommendResponse.builder()
                .totalAvailableExpenses(totalAvailableExpenses)
                .availableExpensesByCategoryList(availableExpensesByCategoryList).build();
    }

}
