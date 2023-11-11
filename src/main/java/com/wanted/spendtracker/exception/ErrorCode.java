package com.wanted.spendtracker.exception;

import static org.springframework.http.HttpStatus.*;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    COMMON_INVALID_PARAMETER("잘못된 파라미터입니다.", BAD_REQUEST),
    COMMON_SERVER_ERROR("서버에서 에러가 발생하였습니다.", INTERNAL_SERVER_ERROR),

    MEMBER_PASSWORD_SHORT("비밀번호는 10자 이상이어야 합니다.", BAD_REQUEST),
    MEMBER_PASSWORD_REPEATED("3회 이상 연속되는 문자를 포함한 비밀번호는 사용할 수 없습니다.", BAD_REQUEST),
    MEMBER_PASSWORD_SIMPLE("비밀번호는 숫자, 영문, 특수문자 중 2가지 이상을 포함해야 합니다.", BAD_REQUEST),
    MEMBER_PASSWORD_PERSONAL_INFO("다른 개인정보와 유사한 비밀번호는 사용할 수 없습니다.", BAD_REQUEST),
    MEMBER_ACCOUNT_NAME_DUPLICATED("이미 같은 이름의 계정이 존재합니다", BAD_REQUEST)

    ;

    private final String message;
    private final HttpStatus httpStatus;

}
