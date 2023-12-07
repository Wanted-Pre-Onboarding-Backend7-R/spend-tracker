package com.wanted.spendtracker.domain.category.service;

import com.wanted.spendtracker.domain.category.domain.Category;
import com.wanted.spendtracker.domain.category.dto.CategoryGetResponse;
import com.wanted.spendtracker.domain.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Transactional(readOnly = true)
    public List<CategoryGetResponse> getAllCategory() {
        List<Category> categoryList = categoryRepository.findAll();
        return categoryList.stream()
                .map(CategoryGetResponse::from)
                .toList();
    }

}
