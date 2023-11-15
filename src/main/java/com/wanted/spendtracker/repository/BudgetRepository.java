package com.wanted.spendtracker.repository;

import com.wanted.spendtracker.domain.Budget;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BudgetRepository extends JpaRepository<Budget, Long> {
}
