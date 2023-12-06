package com.wanted.spendtracker.domain.category.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class CategoryAmountResponse {

    private final Long categoryId;
    private final Long amount;

    @Builder
    public CategoryAmountResponse(Long categoryId, Long amount) {
        this.categoryId = categoryId;
        this.amount = amount;
    }

    public static CategoryAmountResponse of(Long categoryId, Long amount) {
        return CategoryAmountResponse.builder()
                .categoryId(categoryId)
                .amount(amount).build();
    }

    public CategoryAmountResponse(Long categoryId, double amount) {
        this.categoryId = categoryId;
        this.amount = (long) amount;
    }

}
