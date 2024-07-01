package org.senla.komar.spring.exception;

/**
 * @author Olga Komar
 * Created at 26.05.2024
 */
public class PersonIllegalArgumentException extends RuntimeException {
    public PersonIllegalArgumentException(String message) {
        super(message);
    }
}
