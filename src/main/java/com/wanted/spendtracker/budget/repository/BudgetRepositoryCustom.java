package com.wanted.spendtracker.budget.repository;

import com.wanted.spendtracker.category.dto.CategoryAmountResponse;

import java.util.List;

public interface BudgetRepositoryCustom {
    List<CategoryAmountResponse> getTotalCategoryAmount();
    List<CategoryAmountResponse> getTotalCategoryAmountByMonth(Long memberId, Integer month);

}
