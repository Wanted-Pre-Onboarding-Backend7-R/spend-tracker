package com.wanted.spendtracker.global.docs;

import com.wanted.spendtracker.domain.category.dto.CategoryGetResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Tag(name = "카테고리")
public interface CategoryControllerDocs {

    @Operation(summary = "카테고리 목록 조회 API", description = "전체 카테고리 목록을 조회한다.")
    @ApiResponse(responseCode = "200", description = "OK")
    public ResponseEntity<List<CategoryGetResponse>> getAllCategory();

}
