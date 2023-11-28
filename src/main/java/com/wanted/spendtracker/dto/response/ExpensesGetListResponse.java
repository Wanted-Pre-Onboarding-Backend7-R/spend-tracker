package com.wanted.spendtracker.dto.response;

import com.wanted.spendtracker.domain.Expenses;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class ExpensesGetListResponse {
    private final Long totalExpensesAmount;
    private final List<CategoryAmountResponse> totalCategoryAmountList;
    private final List<ExpensesGetResponse> expensesList;
    private final Long totalElements;
    private final Integer totalPages;


    @Builder
    private ExpensesGetListResponse(List<ExpensesGetResponse> expensesList,
                                    Long totalExpensesAmount,
                                    List<CategoryAmountResponse> totalCategoryAmountList, Long totalElements, Integer totalPages) {
        this.expensesList = expensesList;
        this.totalExpensesAmount = totalExpensesAmount;
        this.totalCategoryAmountList = totalCategoryAmountList;
        this.totalElements = totalElements;
        this.totalPages = totalPages;
    }

    public static ExpensesGetListResponse of(List<ExpensesGetResponse> expensesList,
                                             Long totalExpensesAmount,
                                             List<CategoryAmountResponse> totalCategoryAmountList,
                                             Page<Expenses> expensesPage) {
        return ExpensesGetListResponse.builder()
                .expensesList(expensesList)
                .totalExpensesAmount(totalExpensesAmount)
                .totalCategoryAmountList(totalCategoryAmountList)
                .totalElements(expensesPage.getTotalElements())
                .totalPages(expensesPage.getTotalPages()).build();
    }

}
