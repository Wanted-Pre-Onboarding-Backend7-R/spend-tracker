package com.wanted.spendtracker.global.docs;

import com.wanted.spendtracker.domain.auth.domain.MemberAdapter;
import com.wanted.spendtracker.domain.expenses.dto.request.ExpensesCreateRequest;
import com.wanted.spendtracker.domain.expenses.dto.request.ExpensesGetListRequest;
import com.wanted.spendtracker.domain.expenses.dto.request.ExpensesUpdateRequest;
import com.wanted.spendtracker.domain.expenses.dto.response.ExpensesGetListResponse;
import com.wanted.spendtracker.domain.expenses.dto.response.ExpensesGetResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

@Tag(name = "지출 기록")
public interface ExpenseControllerDocs {

    @Operation(summary = "지출 생성 API", description = "사용자가 지출을 생성한다.")
    @ApiResponse(responseCode = "201", description = "CREATED")
    public ResponseEntity<Void> createExpenses(MemberAdapter memberAdapter,
                                               ExpensesCreateRequest expensesCreateRequest);

    @Operation(summary = "지출 수정 API", description = "사용자가 지출을 수정한다.")
    @ApiResponse(responseCode = "200", description = "OK")
    public ResponseEntity<Void> updateExpenses(MemberAdapter memberAdapter,
                                               @Parameter(description = "지출 id") Long expensesId,
                                               ExpensesUpdateRequest expensesUpdateRequest);

    @Operation(summary = "지출 상세 조회 API", description = "사용자가 특정 지출 내역을 상세 조회한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(schema = @Schema(implementation = ExpensesGetResponse.class))}),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST")
    })
    public ResponseEntity<ExpensesGetResponse> getExpenses(MemberAdapter memberAdapter,
                                                           @Parameter(description = "지출 id") Long expensesId);

    @Operation(summary = "지출 목록 조회 API", description = "사용자가 지출 내역을 필터링 조건으로 조회한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "OK", content = {@Content(schema = @Schema(implementation = ExpensesGetListResponse.class))}),
            @ApiResponse(responseCode = "400", description = "BAD_REQUEST")
    })
    public ResponseEntity<ExpensesGetListResponse> getExpensesList(MemberAdapter memberAdapter,
                                                                   ExpensesGetListRequest expensesGetRequest,
                                                                   Pageable pageable);

    @Operation(summary = "지출 삭제 API", description = "사용자가 특정 지출을 삭제한다.")
    @ApiResponse(responseCode = "200", description = "OK")
    public ResponseEntity<Void> deleteExpenses(MemberAdapter memberAdapter,
                                               @Parameter(description = "지출 id") Long expensesId);

}
