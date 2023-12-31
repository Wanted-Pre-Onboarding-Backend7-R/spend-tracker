package com.wanted.spendtracker.domain.budget.dto.request;

import com.wanted.spendtracker.global.validation.AmountUnit;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BudgetRecommendRequest {

    @Schema(description = "예산 총액", example = "850000")
    @NotNull(message = "BUDGET_AMOUNT_EMPTY")
    @PositiveOrZero(message = "BUDGET_AMOUNT_INVALID")
    @AmountUnit
    private Long amount;

    @Builder
    private BudgetRecommendRequest(Long amount) {
        this.amount = amount;
    }

}
