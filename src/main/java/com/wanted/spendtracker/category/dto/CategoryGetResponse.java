package com.wanted.spendtracker.category.dto;

import com.wanted.spendtracker.category.domain.Category;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CategoryGetResponse {

    private final Long categoryId;
    private final String categoryName;

    @Builder
    private CategoryGetResponse(Long categoryId, String categoryName) {
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public static CategoryGetResponse from(Category category) {
        return CategoryGetResponse.builder()
                .categoryId(category.getId())
                .categoryName(category.getName())
                .build();
    }

}
