package com.wanted.spendtracker.domain.category.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QCategory is a Querydsl query type for Category
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCategory extends EntityPathBase<Category> {

    private static final long serialVersionUID = 2132998944L;

    public static final QCategory category = new QCategory("category");

    public final ListPath<com.wanted.spendtracker.domain.budget.domain.Budget, com.wanted.spendtracker.domain.budget.domain.QBudget> budgets = this.<com.wanted.spendtracker.domain.budget.domain.Budget, com.wanted.spendtracker.domain.budget.domain.QBudget>createList("budgets", com.wanted.spendtracker.domain.budget.domain.Budget.class, com.wanted.spendtracker.domain.budget.domain.QBudget.class, PathInits.DIRECT2);

    public final ListPath<com.wanted.spendtracker.domain.expenses.domain.Expenses, com.wanted.spendtracker.domain.expenses.domain.QExpenses> expenses = this.<com.wanted.spendtracker.domain.expenses.domain.Expenses, com.wanted.spendtracker.domain.expenses.domain.QExpenses>createList("expenses", com.wanted.spendtracker.domain.expenses.domain.Expenses.class, com.wanted.spendtracker.domain.expenses.domain.QExpenses.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath name = createString("name");

    public QCategory(String variable) {
        super(Category.class, forVariable(variable));
    }

    public QCategory(Path<? extends Category> path) {
        super(path.getType(), path.getMetadata());
    }

    public QCategory(PathMetadata metadata) {
        super(Category.class, metadata);
    }

}

