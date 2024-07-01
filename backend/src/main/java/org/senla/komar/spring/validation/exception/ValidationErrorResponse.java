package org.senla.komar.spring.validation.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

/**
 * Created 20/05/2024 - 20:01
 * @author Olga Komar
 */

@Getter
@RequiredArgsConstructor
public class ValidationErrorResponse {

    private final List<Violation> violations;

}

