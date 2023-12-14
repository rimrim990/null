package com.project.undefined.common.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Objects;

public class EnumValidator implements ConstraintValidator<Enum, String> {

    private Enum annotation;

    @Override
    public void initialize(Enum constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Object[] enumValues = this.annotation.target().getEnumConstants();

        if (Objects.nonNull(enumValues)) {
            return Arrays.stream(enumValues)
                .anyMatch(enumValue -> value.equalsIgnoreCase(enumValue.toString()));
        }

        return false;
    }
}
