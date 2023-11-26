package com.wanted.spendtracker.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class BudgetRecommendResponse {

    private final Long totalAmount;
    private final List<CategoryAmountResponse> categoryAmountResponses;

    @Builder
    private BudgetRecommendResponse(Long totalAmount, List<CategoryAmountResponse> categoryAmountResponses) {
        this.totalAmount = totalAmount;
        this.categoryAmountResponses = categoryAmountResponses;
    }

    public static BudgetRecommendResponse of(Long totalAmount, List<CategoryAmountResponse> categoryAmountResponses) {
        return BudgetRecommendResponse.builder()
                .totalAmount(totalAmount)
                .categoryAmountResponses(categoryAmountResponses).build();
    }

}
