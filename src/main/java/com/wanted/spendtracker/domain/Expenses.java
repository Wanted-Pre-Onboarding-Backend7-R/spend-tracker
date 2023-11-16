package com.wanted.spendtracker.domain;

import com.wanted.spendtracker.dto.request.ExpensesCreateRequest;
import com.wanted.spendtracker.dto.request.ExpensesUpdateRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Expenses extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private Long amount;

    @Lob
    private String memo;

    //전체 지출 합계 시 포함 여부
    @Column(nullable = false)
    private Boolean excludeFromTotalAmount = false;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Builder
    private Expenses(LocalDate date, Long amount, String memo, boolean excludeFromTotalAmount, Member member, Category category) {
        this.date = date;
        this.amount = amount;
        this.memo = memo;
        this.excludeFromTotalAmount = excludeFromTotalAmount;
        this.member = member;
        this.category = category;
    }

    public static Expenses of(ExpensesCreateRequest expensesCreateRequest, Member member, Category category) {
        return Expenses.builder()
                .date(expensesCreateRequest.getDate())
                .amount(expensesCreateRequest.getAmount())
                .memo(expensesCreateRequest.getMemo())
                .excludeFromTotalAmount(expensesCreateRequest.getExcludeFromTotalAmount())
                .member(member)
                .category(category).build();
    }

    public void update(ExpensesUpdateRequest expensesUpdateRequest) {
        updateDate(expensesUpdateRequest.getDate());
        updateAmount(expensesUpdateRequest.getAmount());
        updateMemo(expensesUpdateRequest.getMemo());
        updateExcludeFromTotalAmount(expensesUpdateRequest.getExcludeFromTotalAmount());
    }

    private void updateDate(LocalDate date) {
        if (date != null) {
            this.date = date;
        }
    }

    private void updateAmount(Long amount) {
        if (amount != null) {
            this.amount = amount;
        }
    }

    private void updateMemo(String memo) {
        if (StringUtils.hasText(memo)) {
            this.memo = memo;
        }
    }

    private void updateExcludeFromTotalAmount(Boolean excludeFromTotalAmount) {
        if (excludeFromTotalAmount != null) {
            this.excludeFromTotalAmount = excludeFromTotalAmount;
        }
    }

}
