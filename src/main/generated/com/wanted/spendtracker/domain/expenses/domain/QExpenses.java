package com.wanted.spendtracker.domain.expenses.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QExpenses is a Querydsl query type for Expenses
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QExpenses extends EntityPathBase<Expenses> {

    private static final long serialVersionUID = 1037577562L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QExpenses expenses = new QExpenses("expenses");

    public final com.wanted.spendtracker.global.common.QBaseTimeEntity _super = new com.wanted.spendtracker.global.common.QBaseTimeEntity(this);

    public final NumberPath<Long> amount = createNumber("amount", Long.class);

    public final com.wanted.spendtracker.domain.category.domain.QCategory category;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final DatePath<java.time.LocalDate> date = createDate("date", java.time.LocalDate.class);

    public final BooleanPath excludeFromTotalAmount = createBoolean("excludeFromTotalAmount");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.wanted.spendtracker.domain.member.domain.QMember member;

    public final StringPath memo = createString("memo");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> updatedAt = _super.updatedAt;

    public QExpenses(String variable) {
        this(Expenses.class, forVariable(variable), INITS);
    }

    public QExpenses(Path<? extends Expenses> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QExpenses(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QExpenses(PathMetadata metadata, PathInits inits) {
        this(Expenses.class, metadata, inits);
    }

    public QExpenses(Class<? extends Expenses> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new com.wanted.spendtracker.domain.category.domain.QCategory(forProperty("category")) : null;
        this.member = inits.isInitialized("member") ? new com.wanted.spendtracker.domain.member.domain.QMember(forProperty("member")) : null;
    }

}

