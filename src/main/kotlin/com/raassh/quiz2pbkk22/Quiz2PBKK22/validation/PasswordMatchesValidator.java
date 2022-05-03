package com.raassh.quiz2pbkk22.Quiz2PBKK22.validation;

import com.raassh.quiz2pbkk22.Quiz2PBKK22.form.RegisterForm;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class PasswordMatchesValidator
        implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(PasswordMatches constraintAnnotation) {
    }

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        RegisterForm registerForm = (RegisterForm) obj;
        return Objects.equals(registerForm.getPassword(), registerForm.getPassword_confirmation());
    }
}
