package org.senla.komar.spring.validation.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * Created 20/05/2024 - 20:02
 *
 * @author Olga Komar
 */
@Getter
@RequiredArgsConstructor
public class Violation {

    private final String fieldName;
    private final String message;

}