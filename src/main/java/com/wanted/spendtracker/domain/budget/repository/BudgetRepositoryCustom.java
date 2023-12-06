package com.wanted.spendtracker.domain.budget.repository;

import com.wanted.spendtracker.domain.category.dto.CategoryAmountResponse;

import java.util.List;

public interface BudgetRepositoryCustom {
    List<CategoryAmountResponse> getTotalCategoryAmount();
    List<CategoryAmountResponse> getTotalCategoryAmountByMonth(Long memberId, Integer month);

}
