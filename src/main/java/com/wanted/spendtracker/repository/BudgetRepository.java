package com.wanted.spendtracker.repository;

import com.wanted.spendtracker.domain.Budget;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BudgetRepository extends JpaRepository<Budget, Long>, BudgetRepositoryCustom {
    @Query("SELECT sum(b.amount) FROM Budget b")
    Long getTotalBudgetAmount();

}
