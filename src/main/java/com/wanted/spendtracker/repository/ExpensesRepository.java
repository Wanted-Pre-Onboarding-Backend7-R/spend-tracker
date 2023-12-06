package com.wanted.spendtracker.repository;

import com.wanted.spendtracker.domain.Expenses;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;

public interface ExpensesRepository extends JpaRepository<Expenses, Long>, ExpensesRepositoryCustom {

    @Query("SELECT sum(e.amount) FROM Expenses e where e.member.id = :memberId and e.date = :currentDate")
    Long getTotalExpensesByToday(Long memberId, LocalDate currentDate);

}
