package com.wanted.spendtracker.category.repository;

import com.wanted.spendtracker.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
