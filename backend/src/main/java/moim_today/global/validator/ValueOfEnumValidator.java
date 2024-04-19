package moim_today.global.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import moim_today.global.annotation.ValidEnum;

import java.util.Arrays;

public class ValueOfEnumValidator implements ConstraintValidator<ValidEnum, String> {

    private ValidEnum validEnum;

    @Override
    public void initialize(final ValidEnum constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(final String value, final ConstraintValidatorContext constraintValidatorContext) {
        boolean result = false;
        Object[] enumValues = this.validEnum.enumClass().getEnumConstants();

        return enumValues != null &&
                Arrays.stream(enumValues)
                        .anyMatch(enumValue -> value.equals(enumValue.toString()));
    }
}
