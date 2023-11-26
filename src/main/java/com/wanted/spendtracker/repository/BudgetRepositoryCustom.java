package com.wanted.spendtracker.repository;

import com.wanted.spendtracker.dto.response.CategoryAmountResponse;

import java.util.List;

public interface BudgetRepositoryCustom {
    List<CategoryAmountResponse> getTotalCategoryAmount();

}
