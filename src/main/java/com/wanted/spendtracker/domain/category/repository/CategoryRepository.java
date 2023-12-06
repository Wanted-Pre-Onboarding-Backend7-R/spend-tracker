package com.wanted.spendtracker.domain.category.repository;

import com.wanted.spendtracker.domain.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
