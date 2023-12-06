package com.wanted.spendtracker.expenses.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExpensesCreateRequest {

    @NotNull(message = "CATEGORY_NOT_EXISTS")
    private Long categoryId;

    @NotNull(message = "EXPENSES_DATE_EMPTY")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate date;

    @NotNull(message = "EXPENSES_AMOUNT_EMPTY")
    @PositiveOrZero(message = "EXPENSES_AMOUNT_INVALID")
    private Long amount;

    private String memo;

    @NotNull(message = "EXPENSES_EXCLUDE_FROM_TOTAL_AMOUNT_EMPTY")
    private Boolean excludeFromTotalAmount;

    @Builder
    private ExpensesCreateRequest(Long categoryId, LocalDate date, Long amount, String memo, Boolean excludeFromTotalAmount) {
        this.categoryId = categoryId;
        this.date = date;
        this.amount = amount;
        this.memo = memo;
        this.excludeFromTotalAmount = excludeFromTotalAmount;
    }

}
