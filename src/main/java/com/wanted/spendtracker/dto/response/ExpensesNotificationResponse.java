package com.wanted.spendtracker.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class ExpensesNotificationResponse {
    private final Long todayTotalExpenses;
    private final List<CategoryAmountResponse> todayTotalCategoryExpensesList;
    private final ExpensesRatioResponse expensesRatioResponse;

    @Builder
    private ExpensesNotificationResponse(Long todayTotalExpenses,
                                        List<CategoryAmountResponse> todayTotalCategoryExpensesList,
                                        ExpensesRatioResponse expensesRatioResponse) {
        this.todayTotalExpenses = todayTotalExpenses;
        this.todayTotalCategoryExpensesList = todayTotalCategoryExpensesList;
        this.expensesRatioResponse = expensesRatioResponse;
    }

    public static ExpensesNotificationResponse of(Long todayTotalExpenses,
                                                  List<CategoryAmountResponse> todayTotalCategoryExpensesList,
                                                  ExpensesRatioResponse expensesRatioResponse) {
        return ExpensesNotificationResponse.builder()
                .todayTotalExpenses(todayTotalExpenses)
                .todayTotalCategoryExpensesList(todayTotalCategoryExpensesList)
                .expensesRatioResponse(expensesRatioResponse).build();
    }

}
