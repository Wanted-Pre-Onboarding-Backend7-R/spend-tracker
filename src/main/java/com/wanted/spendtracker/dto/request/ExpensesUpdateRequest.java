package com.wanted.spendtracker.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ExpensesUpdateRequest {

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd", timezone = "Asia/Seoul")
    private LocalDate date;

    @PositiveOrZero(message = "EXPENSES_AMOUNT_INVALID")
    private Long amount;

    private String memo;

    private Boolean excludeFromTotalAmount;

    @Builder
    private ExpensesUpdateRequest(LocalDate date, Long amount, String memo, Boolean excludeFromTotalAmount) {
        this.date = date;
        this.amount = amount;
        this.memo = memo;
        this.excludeFromTotalAmount = excludeFromTotalAmount;
    }

}
