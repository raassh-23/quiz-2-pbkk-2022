package com.raassh.quiz2pbkk22.Quiz2PBKK22.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IsNumericValidator
        implements ConstraintValidator<IsNumeric, String> {

    private Pattern pattern;
    private Matcher matcher;
    private static final String NUMERIC_PATTERN = "-?[0-9]+(\\.[0-9]+)?";

    @Override
    public void initialize(IsNumeric constraintAnnotation) {
    }

    @Override
    public boolean isValid(String data, ConstraintValidatorContext context) {
        return (validate(data));
    }

    private boolean validate(String data) {
        pattern = Pattern.compile(NUMERIC_PATTERN);
        matcher = pattern.matcher(data);
        return matcher.matches();
    }
}
