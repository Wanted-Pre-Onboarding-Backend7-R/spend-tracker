package com.wanted.spendtracker.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AmountUnitValidator implements ConstraintValidator<AmountUnit, Long> {

    public static final int MONEY_UNIT = 100;

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        return value != null && value % MONEY_UNIT == 0;
    }

}
