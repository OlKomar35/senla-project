package org.senla.komar.spring.security.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AuthException extends RuntimeException {
    private HttpStatus status;
    private String message;

    public AuthException(HttpStatus status, String message) {
        this.status = status;
        this.message = message;

    }
}
