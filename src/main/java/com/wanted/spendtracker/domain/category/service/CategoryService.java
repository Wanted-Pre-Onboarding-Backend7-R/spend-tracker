package com.wanted.spendtracker.domain.category.service;

import com.wanted.spendtracker.domain.category.domain.Category;
import com.wanted.spendtracker.domain.category.dto.CategoryGetResponse;
import com.wanted.spendtracker.domain.category.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryGetResponse> getAllCategory() {
        List<Category> categories = categoryRepository.findAll();
        List<CategoryGetResponse> categoryGetResponses = new ArrayList<>();
        for(Category category : categories) {
            CategoryGetResponse categoryGetResponse = CategoryGetResponse.from(category);
            categoryGetResponses.add(categoryGetResponse);
        }
        return categoryGetResponses;
    }

}
