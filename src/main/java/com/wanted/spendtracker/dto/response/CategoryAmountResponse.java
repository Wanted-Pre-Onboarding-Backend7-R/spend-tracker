package com.wanted.spendtracker.dto.response;

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

    public CategoryAmountResponse(Long categoryId, double amount) {
        this.categoryId = categoryId;
        this.amount = (long) amount;
    }

}
