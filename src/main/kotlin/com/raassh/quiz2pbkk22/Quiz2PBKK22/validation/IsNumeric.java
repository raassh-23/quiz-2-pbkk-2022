package com.raassh.quiz2pbkk22.Quiz2PBKK22.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = IsNumericValidator.class)
@Documented
public @interface IsNumeric {
    String message() default "Must be numeric";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

