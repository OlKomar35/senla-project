package org.senla.komar.spring.validation.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.senla.komar.spring.validation.RussianCapitalLettersValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


/**
 * Created 20/05/2024 - 19:52
 *
 * @author Olga Komar
 */

@Target({ElementType.PARAMETER, ElementType.FIELD, ElementType.METHOD, ElementType.TYPE, ElementType.CONSTRUCTOR})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RussianCapitalLettersValidator.class)
public @interface RussianCapitalLetters {
    String message() default "Допускаются только русские заглавные буквы";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}