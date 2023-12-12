package com.wanted.spendtracker.domain.category.controller;

import com.wanted.spendtracker.domain.category.dto.CategoryGetResponse;
import com.wanted.spendtracker.domain.category.service.CategoryService;
import com.wanted.spendtracker.global.docs.CategoryControllerDocs;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class CategoryController implements CategoryControllerDocs {

    private final CategoryService categoryService;

    @GetMapping("/api/categories")
    public ResponseEntity<List<CategoryGetResponse>> getAllCategory() {
        List<CategoryGetResponse> categoryGetResponses = categoryService.getAllCategory();
        return ResponseEntity.ok(categoryGetResponses);
    }

}
