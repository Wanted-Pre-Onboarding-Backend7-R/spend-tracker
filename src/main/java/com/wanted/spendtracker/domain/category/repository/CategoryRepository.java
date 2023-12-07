package com.wanted.spendtracker.domain.category.repository;

import com.wanted.spendtracker.domain.category.domain.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    @Query("SELECT c.name FROM Category c where c.id = :categoryId")
    String getCategoryNameById(Long categoryId);

}
