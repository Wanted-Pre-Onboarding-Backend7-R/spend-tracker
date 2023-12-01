package com.wanted.spendtracker.repository;

import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.BooleanPath;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.wanted.spendtracker.domain.Expenses;
import com.wanted.spendtracker.domain.Member;
import com.wanted.spendtracker.dto.request.ExpensesGetListRequest;
import com.wanted.spendtracker.dto.response.CategoryAmountResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

import java.time.LocalDate;
import java.util.List;

import static com.wanted.spendtracker.domain.QExpenses.expenses;

@RequiredArgsConstructor
public class ExpensesRepositoryImpl implements ExpensesRepositoryCustom {

    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Page<Expenses> findAllByExpensesGetRequest(Member member, ExpensesGetListRequest expensesGetRequest, Pageable pageable) {
        List<Expenses> expensesList = jpaQueryFactory
                .selectFrom(expenses)
                .where(
                        memberEq(member.getId()),
                        categoryEq(expensesGetRequest.getCategoryId()),
                        startDateAfter(expensesGetRequest.getStartDate()),
                        endDateBefore(expensesGetRequest.getEndDate()),
                        minAmountGoe(expensesGetRequest.getMinAmount()),
                        maxAmountLoe(expensesGetRequest.getMaxAmount())
                )
                .orderBy(expenses.date.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        JPAQuery<Long> countQuery = jpaQueryFactory
                .select(expenses.count())
                .from(expenses)
                .where(
                        memberEq(member.getId()),
                        categoryEq(expensesGetRequest.getCategoryId()),
                        startDateAfter(expensesGetRequest.getStartDate()),
                        endDateBefore(expensesGetRequest.getEndDate()),
                        minAmountGoe(expensesGetRequest.getMinAmount()),
                        maxAmountLoe(expensesGetRequest.getMaxAmount())
                );

        return PageableExecutionUtils.getPage(expensesList, pageable, countQuery::fetchOne);
    }

    @Override
    public List<CategoryAmountResponse> findTotalCategoryAmountByRequest(Member member, ExpensesGetListRequest expensesGetRequest) {
        return jpaQueryFactory
                .select(Projections.constructor(CategoryAmountResponse.class,
                        expenses.category.id.as("category_id"),
                        expenses.amount.sum().as("amount"))
                )
                .from(expenses)
                .where(
                        memberEq(member.getId()),
                        categoryEq(expensesGetRequest.getCategoryId()),
                        startDateAfter(expensesGetRequest.getStartDate()),
                        endDateBefore(expensesGetRequest.getEndDate()),
                        minAmountGoe(expensesGetRequest.getMinAmount()),
                        maxAmountLoe(expensesGetRequest.getMaxAmount()),
                        isExcluded(expenses.excludeFromTotalAmount)
                )
                .groupBy(expenses.category.id)
                .fetch();
    }

    @Override
    public Long getTotalExpensesAmountUntilToday(Member member, LocalDate currentDate) {
        LocalDate firstDayOfMonth = currentDate.withDayOfMonth(1);
        return jpaQueryFactory
                .select(expenses.amount.sum())
                .from(expenses)
                .where(
                        memberEq(member.getId()),
                        startDateAfter(firstDayOfMonth),
                        endDateBefore(currentDate),
                        isExcluded(expenses.excludeFromTotalAmount)
                )
                .fetchOne();
    }

    @Override
    public List<CategoryAmountResponse> getTodayTotalCategoryExpenses(Member member, LocalDate currentDate) {
        return jpaQueryFactory
                .select(Projections.constructor(CategoryAmountResponse.class,
                        expenses.category.id.as("category_id"),
                        expenses.amount.sum().as("amount"))
                )
                .from(expenses)
                .where(
                        memberEq(member.getId()),
                        currentDateEq(currentDate)
                )
                .groupBy(expenses.category.id)
                .fetch();
    }

    private BooleanExpression currentDateEq(LocalDate currentDate) {
        return currentDate != null ? expenses.date.eq(currentDate) : null;
    }

    private BooleanExpression isExcluded(BooleanPath excludeFromTotalAmount) {
        return excludeFromTotalAmount != null ? excludeFromTotalAmount.eq(false) : null;
    }

    private BooleanExpression maxAmountLoe(Long maxAmount) {
        return maxAmount != null ? expenses.amount.loe(maxAmount) : null;
    }

    private BooleanExpression minAmountGoe(Long minAmount) {
        return minAmount != null ? expenses.amount.goe(minAmount) : null;
    }

    private BooleanExpression endDateBefore(LocalDate endDate) {
        return endDate != null ? expenses.date.loe(endDate) : null;
    }

    private BooleanExpression startDateAfter(LocalDate startDate) {
        return startDate != null ? expenses.date.goe(startDate) : null;
    }

    private BooleanExpression categoryEq(Long categoryId) {
        return categoryId != null ? expenses.category.id.eq(categoryId) : null;
    }

    private BooleanExpression memberEq(Long memberId) {
        return memberId != null ? expenses.member.id.eq(memberId) : null;
    }

}
