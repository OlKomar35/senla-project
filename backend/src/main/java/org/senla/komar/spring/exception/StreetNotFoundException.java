
package org.senla.komar.spring.exception;


public class StreetNotFoundException extends RuntimeException {

    public StreetNotFoundException(String message) {
        super(message);
    }
}
