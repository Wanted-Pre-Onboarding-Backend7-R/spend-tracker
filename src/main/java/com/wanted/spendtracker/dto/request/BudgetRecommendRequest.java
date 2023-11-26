package com.wanted.spendtracker.dto.request;

import com.wanted.spendtracker.validation.AmountUnit;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BudgetRecommendRequest {

    @NotNull(message = "BUDGET_AMOUNT_EMPTY")
    @Min(value = 0, message = "BUDGET_AMOUNT_INVALID")
    @AmountUnit
    private Long amount;

    @Builder
    private BudgetRecommendRequest(Long amount) {
        this.amount = amount;
    }

}
