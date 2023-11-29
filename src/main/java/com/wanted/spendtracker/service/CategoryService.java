package com.wanted.spendtracker.service;

import com.wanted.spendtracker.domain.Category;
import com.wanted.spendtracker.dto.response.CategoryGetResponse;
import com.wanted.spendtracker.repository.CategoryRepository;
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
