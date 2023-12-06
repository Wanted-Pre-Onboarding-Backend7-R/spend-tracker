package com.wanted.spendtracker.domain.category.controller;

import com.wanted.spendtracker.domain.category.dto.CategoryGetResponse;
import com.wanted.spendtracker.domain.category.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/api/categories")
    public ResponseEntity<List<CategoryGetResponse>> getAllCategory() {
        List<CategoryGetResponse> categoryGetResponses = categoryService.getAllCategory();
        return ResponseEntity.ok(categoryGetResponses);
    }

}
