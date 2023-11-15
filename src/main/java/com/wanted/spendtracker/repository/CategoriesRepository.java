package com.wanted.spendtracker.repository;

import com.wanted.spendtracker.domain.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriesRepository extends JpaRepository<Categories, Long> {
}
