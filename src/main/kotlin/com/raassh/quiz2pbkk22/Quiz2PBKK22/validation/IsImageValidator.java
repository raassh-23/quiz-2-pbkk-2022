package com.raassh.quiz2pbkk22.Quiz2PBKK22.validation;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class IsImageValidator
        implements ConstraintValidator<IsImage, MultipartFile> {
    @Override
    public void initialize(IsImage constraintAnnotation) {
    }

    @Override
    public boolean isValid(MultipartFile data, ConstraintValidatorContext context) {
        return data.isEmpty() || data.getContentType().startsWith("image/");
    }
}
