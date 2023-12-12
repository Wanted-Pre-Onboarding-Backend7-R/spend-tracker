package com.wanted.spendtracker.global.docs;

import com.wanted.spendtracker.domain.member.dto.MemberSignUpRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "사용자")
public interface MemberControllerDocs {

    @Operation(summary = "사용자 생성 API", description = "사용자를 생성한다.")
    @ApiResponse(responseCode = "201", description = "CREATED")
    ResponseEntity<Void> signUp(MemberSignUpRequest memberSignUpRequest);

}
