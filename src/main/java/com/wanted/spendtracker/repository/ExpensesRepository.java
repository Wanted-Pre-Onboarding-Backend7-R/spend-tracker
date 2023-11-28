package com.wanted.spendtracker.repository;

import com.wanted.spendtracker.domain.Expenses;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpensesRepository extends JpaRepository<Expenses, Long>, ExpensesRepositoryCustom {
}
