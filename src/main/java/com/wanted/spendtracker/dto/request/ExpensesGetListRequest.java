package com.wanted.spendtracker.dto.request;

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

    private Long categoryId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate endDate;

    @PositiveOrZero(message = "EXPENSES_AMOUNT_INVALID")
    private Long minAmount;

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
