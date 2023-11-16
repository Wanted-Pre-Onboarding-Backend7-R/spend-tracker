package com.wanted.spendtracker.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Budget extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long amount;

    @Column(nullable = false)
    private Integer month;

    @ManyToOne
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne
    @JoinColumn(name = "catefory_id", nullable = false)
    private Category category;

    @Builder
    private Budget(Long amount, int month, Member member, Category category) {
        this.amount = amount;
        this.month = month;
        this.member = member;
        this.category = category;
    }

    public static Budget of(Long amount, Integer month, Member member, Category category) {
        return Budget.builder()
                .amount(amount)
                .month(month)
                .member(member)
                .category(category).build();
    }

}
