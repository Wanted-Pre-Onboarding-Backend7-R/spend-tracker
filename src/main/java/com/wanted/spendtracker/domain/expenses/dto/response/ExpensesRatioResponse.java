package com.wanted.spendtracker.domain.expenses.dto.response;

import lombok.Builder;
import lombok.Getter;

@Getter
public class ExpensesRatioResponse {
    private final Long availableExpenses;
    private final Long todayExpenses;
    private final Double ratioOfExpenses;

    @Builder
    private ExpensesRatioResponse(Long availableExpenses, Long todayExpenses, Double ratioOfExpenses) {
        this.availableExpenses = availableExpenses;
        this.todayExpenses = todayExpenses;
        this.ratioOfExpenses = ratioOfExpenses;
    }

    public static ExpensesRatioResponse of(Long availableExpenses, Long todayExpenses, Double ratioOfExpenses) {
        return ExpensesRatioResponse.builder()
                .availableExpenses(availableExpenses)
                .todayExpenses(todayExpenses)
                .ratioOfExpenses(ratioOfExpenses).build();
    }

}
