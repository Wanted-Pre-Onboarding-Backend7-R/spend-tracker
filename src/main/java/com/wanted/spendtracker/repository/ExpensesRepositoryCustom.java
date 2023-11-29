package com.wanted.spendtracker.repository;

import com.wanted.spendtracker.domain.Expenses;
import com.wanted.spendtracker.domain.Member;
import com.wanted.spendtracker.dto.request.ExpensesGetListRequest;
import com.wanted.spendtracker.dto.response.CategoryAmountResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;

public interface ExpensesRepositoryCustom {
    Page<Expenses> findAllByExpensesGetRequest(Member member, ExpensesGetListRequest expensesGetRequest, Pageable pageable);
    List<CategoryAmountResponse> findTotalCategoryAmountByRequest(Member member, ExpensesGetListRequest expensesGetRequest);
    Long getTotalExpensesAmountUntilToday(Member member, LocalDate currentDate);

}
