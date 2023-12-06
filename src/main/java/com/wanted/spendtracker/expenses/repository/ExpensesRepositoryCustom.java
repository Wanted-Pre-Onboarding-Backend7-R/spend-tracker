package com.wanted.spendtracker.expenses.repository;

import com.wanted.spendtracker.expenses.domain.Expenses;
import com.wanted.spendtracker.expenses.dto.request.ExpensesGetListRequest;
import com.wanted.spendtracker.member.domain.Member;
import com.wanted.spendtracker.category.dto.CategoryAmountResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface ExpensesRepositoryCustom {
    Page<Expenses> findAllByExpensesGetRequest(Member member, ExpensesGetListRequest expensesGetRequest, Pageable pageable);
    List<CategoryAmountResponse> findTotalCategoryAmountByRequest(Member member, ExpensesGetListRequest expensesGetRequest);
    Long getTotalExpensesAmountUntilToday(Member member, LocalDate currentDate);
    List<CategoryAmountResponse> getTodayTotalCategoryExpenses(Member member, LocalDate currentDate);

}
