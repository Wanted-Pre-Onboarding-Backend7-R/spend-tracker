package com.wanted.spendtracker.budget.repository;

import com.wanted.spendtracker.budget.domain.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BudgetRepository extends JpaRepository<Budget, Long>, BudgetRepositoryCustom {
    @Query("SELECT sum(b.amount) FROM Budget b")
    Long getTotalBudgetAmount();

    @Query("SELECT sum(b.amount) FROM Budget b where b.member.id = :memberId and b.month = :month")
    Optional<Long> getTotalBudgetAmountByMonth(Long memberId, Integer month);

}
