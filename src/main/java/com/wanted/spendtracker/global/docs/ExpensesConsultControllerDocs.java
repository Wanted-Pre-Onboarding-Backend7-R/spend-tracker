package com.wanted.spendtracker.global.docs;

import com.wanted.spendtracker.domain.auth.domain.MemberAdapter;
import com.wanted.spendtracker.domain.expenses.dto.response.ExpensesNotificationResponse;
import com.wanted.spendtracker.domain.expenses.dto.response.ExpensesRecommendResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "지출 컨설팅")
public interface ExpensesConsultControllerDocs {

    @Operation(summary = "지출 추천 API", description = "오늘 지출 가능한 금액을 총액과 카테고리별 금액으로 추천한다.")
    @ApiResponse(responseCode = "200", description = "OK")
    public ResponseEntity<ExpensesRecommendResponse> recommendExpenses(MemberAdapter memberAdapter);


    @Operation(summary = "지출 안내 API", description = "오늘 지출한 금액을 총액과 카테고리별 금액으로 안내한다.")
    @ApiResponse(responseCode = "200", description = "OK")
    public ResponseEntity<ExpensesNotificationResponse> notifyExpenses(MemberAdapter memberAdapter);

}
