package com.wanted.spendtracker.domain.budget.dto.request;

import com.wanted.spendtracker.global.validation.AmountUnit;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BudgetSetRequest {

    @Schema(description = "예산 설정 요청 리스트")
    @Valid
    @NotNull(message = "BUDGET_REQUEST_EMPTY")
    List<BudgetRequest> budgetRequestList;

    @Getter
    public static class BudgetRequest {

        @Schema(description = "예산 월", example = "12")
        @NotNull(message = "BUDGET_MONTH_EMPTY")
        @Min(value = 1, message = "BUDGET_MONTH_INVALID")
        @Max(value = 12, message = "BUDGET_MONTH_INVALID")
        private Integer month;

        @Schema(description = "카테고리 id", example = "1")
        @NotNull(message = "CATEGORY_NOT_EXISTS")
        private Long categoryId;

        @Schema(description = "예산 금액", example = "55000")
        @NotNull(message = "BUDGET_AMOUNT_EMPTY")
        @PositiveOrZero(message = "BUDGET_AMOUNT_INVALID")
        @AmountUnit
        private Long amount;

    }

}
