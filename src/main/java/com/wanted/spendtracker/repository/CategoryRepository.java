package com.wanted.spendtracker.repository;

import com.wanted.spendtracker.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
