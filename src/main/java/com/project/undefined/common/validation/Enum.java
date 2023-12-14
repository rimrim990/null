package com.project.undefined.common.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = EnumValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Enum {

    String message() default "유효한 값이 아닙니다.";
    Class<? extends Payload>[] payload() default {};
    Class<?>[] groups() default {};
    Class<? extends java.lang.Enum<?>> target();
}
