package com.wanted.spendtracker.controller;

import com.wanted.spendtracker.dto.response.CategoryGetResponse;
import com.wanted.spendtracker.service.CategoryService;
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
