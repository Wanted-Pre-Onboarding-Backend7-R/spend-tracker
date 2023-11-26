package com.wanted.spendtracker.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = AmountUnitValidator.class)
public @interface AmountUnit {

    String message() default "AMOUNT_UNIT_INVALID";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
