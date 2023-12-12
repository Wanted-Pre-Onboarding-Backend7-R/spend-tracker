package com.wanted.spendtracker.global.docs;

import com.wanted.spendtracker.domain.auth.domain.MemberAdapter;
import com.wanted.spendtracker.domain.budget.dto.request.BudgetRecommendRequest;
import com.wanted.spendtracker.domain.budget.dto.request.BudgetSetRequest;
import com.wanted.spendtracker.domain.budget.dto.response.BudgetRecommendResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "예산")
public interface BudgetControllerDocs {

    @Operation(summary = "예산 설정 API", description = "카테고리별 예산 금액을 설정한다.")
    @ApiResponse(responseCode = "200", description = "OK")
    public ResponseEntity<Void> setBudget(MemberAdapter memberAdapter,
                                          BudgetSetRequest budgetSetRequest);

    @Operation(summary = "예산 추천 API", description = "예산 총액을 입력하여 카테고리별 예산을 추천 받는다.")
    @ApiResponse(responseCode = "200", description = "OK")
    public ResponseEntity<BudgetRecommendResponse> recommendBudget(MemberAdapter memberAdapter,
                                                                   BudgetRecommendRequest budgetRecommendRequest);

}
