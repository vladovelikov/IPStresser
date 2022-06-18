package com.ipstresser.app.validation;

import com.ipstresser.app.domain.models.binding.PasswordWrapper;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class PasswordMatchValidator implements ConstraintValidator<PasswordMatch, PasswordWrapper> {

    @Override
    public boolean isValid(PasswordWrapper value, ConstraintValidatorContext context) {
        String password = value.getPassword();
        String confirmationPassword = value.getConfirmPassword();

        return password.equals(confirmationPassword);
    }
}
