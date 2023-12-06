package com.wanted.spendtracker.category.domain;

import com.wanted.spendtracker.budget.domain.Budget;
import com.wanted.spendtracker.expenses.domain.Expenses;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @OneToMany(mappedBy = "category")
    private List<Budget> budgets = new ArrayList<>();

    @OneToMany(mappedBy = "category")
    private List<Expenses> expenses = new ArrayList<>();

}
