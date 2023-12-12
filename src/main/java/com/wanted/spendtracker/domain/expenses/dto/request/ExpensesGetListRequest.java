package com.wanted.spendtracker.domain.expenses.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExpensesGetListRequest {

    @Schema(description = "카테고리 id", example = "1")
    private Long categoryId;

    @Schema(description = "지출 시작 날짜", example = "2023-12-01")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @Schema(description = "지출 종료 날짜", example = "2023-12-11")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @Schema(description = "지출 최소 금액", example = "10000")
    @PositiveOrZero(message = "EXPENSES_AMOUNT_INVALID")
    private Long minAmount;

    @Schema(description = "지출 최대 금액", example = "200000")
    @PositiveOrZero(message = "EXPENSES_AMOUNT_INVALID")
    private Long maxAmount;

    @Builder
    private ExpensesGetListRequest(Long categoryId, LocalDate startDate, LocalDate endDate, Long minAmount, Long maxAmount) {
        this.categoryId = categoryId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.minAmount = minAmount;
        this.maxAmount = maxAmount;
    }

}
