package com.wanted.spendtracker.global.validation;

import com.wanted.spendtracker.domain.member.dto.MemberSignUpRequest;
import com.wanted.spendtracker.global.exception.CustomException;
import com.wanted.spendtracker.global.exception.ErrorCode;

public class PasswordValidator {

    public static void validatePassword(MemberSignUpRequest memberSignUpRequest) {
        String password = memberSignUpRequest.getPassword();
        char[] passwordChars = password.toCharArray();

        int len = passwordChars.length;
        if (len < 10) {
            throw new CustomException(ErrorCode.MEMBER_PASSWORD_SHORT);
        }

        boolean digitExists = false;
        boolean alphabetExists = false;
        boolean specialCharExists = false;
        int countSameChars = 0;
        char prevCh = '\0';
        for (char ch : passwordChars) {
            if (Character.isDigit(ch)) {
                digitExists = true;
            } else if (Character.isAlphabetic(ch)) {
                alphabetExists = true;
            } else {
                specialCharExists = true;
            }
            if (ch == prevCh) {
                if (++countSameChars == 3) {
                    throw new CustomException(ErrorCode.MEMBER_PASSWORD_REPEATED);
                }
            } else {
                countSameChars = 1;
            }
            prevCh = ch;
        }
        if ((!digitExists && !alphabetExists)
                || (!alphabetExists && !specialCharExists)
                || (!specialCharExists && !digitExists)) {
            throw new CustomException(ErrorCode.MEMBER_PASSWORD_SIMPLE);
        }

        if (password.contains(memberSignUpRequest.getAccountName())) {
            throw new CustomException(ErrorCode.MEMBER_PASSWORD_PERSONAL_INFO);
        }

    }

}
