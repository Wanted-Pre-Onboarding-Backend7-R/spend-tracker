package com.wanted.spendtracker.domain.expenses.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "카테고리 id", example = "1")
    @NotNull(message = "CATEGORY_NOT_EXISTS")
    private Long categoryId;

    @Schema(description = "지출 날짜", example = "2023-12-11")
    @NotNull(message = "EXPENSES_DATE_EMPTY")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate date;

    @Schema(description = "지출 금액", example = "55000")
    @NotNull(message = "EXPENSES_AMOUNT_EMPTY")
    @PositiveOrZero(message = "EXPENSES_AMOUNT_INVALID")
    private Long amount;

    @Schema(description = "지출에 대한 메모", example = "점심")
    private String memo;

    @Schema(description = "합계 제외 여부", defaultValue = "false")
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
