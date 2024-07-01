package org.senla.komar.spring.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.senla.komar.spring.validation.annotation.RussianCapitalLetters;

/**
 * Created 20/05/2024 - 19:53
 *
 * @author Olga Komar
 */
public class RussianCapitalLettersValidator implements  ConstraintValidator<RussianCapitalLetters, String> {
    @Override
    public void initialize(RussianCapitalLetters constraintAnnotation) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        return value.matches("[А-ЯЁ]+");
    }
}
