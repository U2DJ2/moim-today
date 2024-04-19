package moim_today.global.annotation;

import jakarta.validation.Constraint;
import moim_today.global.validator.ValueOfEnumValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = ValueOfEnumValidator.class)
public @interface ValidEnum {

    Class<? extends java.lang.Enum<?>> enumClass();

    String message() default "Invalid Enum Value";
}
