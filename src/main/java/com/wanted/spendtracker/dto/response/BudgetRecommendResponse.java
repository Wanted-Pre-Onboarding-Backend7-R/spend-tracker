package com.wanted.spendtracker.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class BudgetRecommendResponse {

    private final Long amount;
    private final List<CategoryAmountResponse> categoryAmountResponses;

    @Builder
    private BudgetRecommendResponse(Long amount, List<CategoryAmountResponse> categoryAmountResponses) {
        this.amount = amount;
        this.categoryAmountResponses = categoryAmountResponses;
    }

    public static BudgetRecommendResponse of(Long amount, List<CategoryAmountResponse> categoryAmountResponses) {
        return BudgetRecommendResponse.builder()
                .amount(amount)
                .categoryAmountResponses(categoryAmountResponses).build();
    }

}
