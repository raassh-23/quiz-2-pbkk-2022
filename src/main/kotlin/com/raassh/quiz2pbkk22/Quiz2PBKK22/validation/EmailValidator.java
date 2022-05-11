package com.raassh.quiz2pbkk22.Quiz2PBKK22.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// ref: https://www.baeldung.com/registration-with-spring-mvc-and-spring-security
// https://stackoverflow.com/questions/8204680/java-regex-email
public class EmailValidator
        implements ConstraintValidator<ValidEmail, String> {

    private static final String EMAIL_PATTERN = "^(?=.{1,64}@)[\\p{L}0-9\\+_-]+(\\.[\\p{L}0-9\\+_-]+)*@"
            + "[^-][\\p{L}0-9\\+-]+(\\.[\\p{L}0-9\\+-]+)*(\\.[\\p{L}]{2,})$";

    @Override
    public void initialize(ValidEmail constraintAnnotation) {
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return email.isEmpty() || matcher.matches();
    }
}
