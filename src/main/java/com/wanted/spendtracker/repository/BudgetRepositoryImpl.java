package com.wanted.spendtracker.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wanted.spendtracker.dto.response.CategoryAmountResponse;
import lombok.RequiredArgsConstructor;

import java.util.List;

import static com.wanted.spendtracker.domain.QBudget.budget;


@RequiredArgsConstructor
public class BudgetRepositoryImpl implements BudgetRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<CategoryAmountResponse> getTotalCategoryAmount() {
        return jpaQueryFactory
                .select(Projections.constructor(CategoryAmountResponse.class,
                        budget.category.id.as("categoryId"),
                        budget.amount.sum().as("amount")))
                .from(budget)
                .groupBy(budget.category.id)
                .fetch();
    }

}
