package com.wanted.spendtracker.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import static org.springframework.http.HttpStatus.*;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    COMMON_INVALID_PARAMETER("잘못된 파라미터입니다.", BAD_REQUEST),
    COMMON_SERVER_ERROR("서버에서 에러가 발생하였습니다.", INTERNAL_SERVER_ERROR),

    MEMBER_PASSWORD_SHORT("비밀번호는 10자 이상이어야 합니다.", BAD_REQUEST),
    MEMBER_PASSWORD_REPEATED("3회 이상 연속되는 문자를 포함한 비밀번호는 사용할 수 없습니다.", BAD_REQUEST),
    MEMBER_PASSWORD_SIMPLE("비밀번호는 숫자, 영문, 특수문자 중 2가지 이상을 포함해야 합니다.", BAD_REQUEST),
    MEMBER_PASSWORD_PERSONAL_INFO("다른 개인정보와 유사한 비밀번호는 사용할 수 없습니다.", BAD_REQUEST),
    MEMBER_PASSWORD_BLANK("비밀번호는 공백일 수 없습니다.", BAD_REQUEST),
    MEMBER_ACCOUNT_NAME_DUPLICATED("이미 같은 이름의 계정이 존재합니다", BAD_REQUEST),
    MEMBER_ACCOUNT_NAME_LENGTH_INVALID("계정 이름은 최소 2자 이상 최대 20자 이하이어야 합니다", BAD_REQUEST),
    MEMBER_ACCOUNT_NAME_BLANK("계정 이름은 공백일 수 없습니다.", BAD_REQUEST),

    AUTH_AUTHENTICATION_FAILED("인증에 실패하셨습니다.", UNAUTHORIZED),
    AUTH_AUTHORIZATION_FAILED("권한이 없습니다.", FORBIDDEN),
    AUTH_ACCOUNT_NAME_BLANK("계정 이름은 공백일 수 없습니다.", BAD_REQUEST),
    AUTH_ACCOUNT_NAME_LENGTH_INVALID("계정 이름은 최소 2자 이상 최대 20자 이하이어야 합니다", BAD_REQUEST),
    AUTH_JWT_CLAIMS_EMPTY("JWT claims 문자열이 비어 있습니다.", UNAUTHORIZED),
    AUTH_JWT_EXPIRED("만료된 토큰입니다.", UNAUTHORIZED),
    AUTH_JWT_INVALID("잘못된 토큰입니다.", UNAUTHORIZED),
    AUTH_JWT_UNPRIVILEGED("권한이 없는 토큰입니다.", FORBIDDEN),
    AUTH_JWT_UNSUPPORTED("지원되지 않는 토큰입니다.", UNAUTHORIZED),
    AUTH_MEMBER_NOT_EXISTS("존재하지 않는 사용자입니다.", BAD_REQUEST),
    AUTH_PASSWORD_BLANK("비밀번호는 공백일 수 없습니다.", BAD_REQUEST),

    BUDGET_MONTH_EMPTY("예산 월이 존재하지 않습니다.", BAD_REQUEST),
    BUDGET_MONTH_INVALID("예산 월이 유효하지 않습니다.", BAD_REQUEST),
    BUDGET_CATEGORY_NOT_EXISTS("카테고리가 존재하지 않습니다.", BAD_REQUEST),
    BUDGET_AMOUNT_EMPTY("예산 금액이 설정되지 않았습니다.", BAD_REQUEST),
    BUDGET_AMOUNT_INVALID("예산 금액이 유효하지 않습니다.", BAD_REQUEST),
    BUDGET_REQUEST_EMPTY("예산 금액 요청이 존재하지 않습니다.", BAD_REQUEST),
    ;

    private final String message;
    private final HttpStatus httpStatus;

}
