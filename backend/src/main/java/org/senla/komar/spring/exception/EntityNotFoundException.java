package org.senla.komar.spring.exception;

/**
 * @author Olga Komar
 * <p>
 * Created at 10.08.2024
 */
public class EntityNotFoundException  extends RuntimeException {
  public EntityNotFoundException(String message) {
    super(message);
  }

}
