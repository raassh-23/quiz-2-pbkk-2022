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
@Constraint(validatedBy = IsImageValidator.class)
@Documented
public @interface IsImage {
    String message() default "must be image";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

