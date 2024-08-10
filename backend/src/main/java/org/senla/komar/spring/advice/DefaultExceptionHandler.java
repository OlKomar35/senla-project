package org.senla.komar.spring.advice;


import jakarta.validation.ConstraintViolationException;
import lombok.extern.log4j.Log4j2;
import org.senla.komar.spring.exception.*;
import org.senla.komar.spring.security.exception.AuthException;
import org.senla.komar.spring.validation.exception.ValidationErrorResponse;
import org.senla.komar.spring.validation.exception.Violation;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Глобальный обработчик исключений для контроллеров.
 *
 * @author Olga Komar
 * @version 1.0
 */

@Log4j2
@RestControllerAdvice(basePackages = {"org.senla.komar.spring.controller"})
public class DefaultExceptionHandler {

  /**
   * Обрабатывает исключения, связанные с отсутствием ресурсов (отель, человек, адрес, город, улица). Записывает
   * сообщение об исключении в журнал, и возвращает ответ с сообщением об ошибке и статусом HTTP 404 (Not Found).
   *
   * @param exception Исключение, которое необходимо обработать.
   * @return Ответ с сообщением об ошибке и статусом HTTP 404.
   */

  @ExceptionHandler({EntityNotFoundException.class, PersonIllegalArgumentException.class,
      AddressIllegalArgumentException.class})
  public ResponseEntity<?> handlerException(RuntimeException exception) {
    log.debug(exception.getMessage(), exception);
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
    return new ResponseEntity<>(exception.getMessage(), headers, HttpStatus.NOT_FOUND);
  }

  /**
   * Обрабатывает исключения, связанные с ошибками валидации аргументов метода. Записывает сообщение об исключении в
   * журнал, и возвращает ответ с информацией о нарушениях валидации и статусом HTTP 400 (Bad Request).
   *
   * @param e Исключение, которое необходимо обработать.
   * @return Объект ответа с информацией о нарушениях валидации.
   */
  @ResponseBody
  @ExceptionHandler(ConstraintViolationException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  public ValidationErrorResponse onConstraintValidationException(
      ConstraintViolationException e
  ) {
    log.debug("Обработка ConstraintViolationException: {}", e.getMessage(), e);
    final List<Violation> violations = e.getConstraintViolations().stream()
        .map(
            violation -> new Violation(
                violation.getPropertyPath().toString(),
                violation.getMessage()
            )
        )
        .collect(Collectors.toList());
    return new ValidationErrorResponse(violations);
  }

  /**
   * Обрабатывает исключения, связанные с ошибками валидации аргументов методов контроллеров. Записывает сообщение об
   * исключении в журнал, и возвращает ответ с информацией о нарушениях валидации и статусом HTTP 400 (Bad Request).
   *
   * @param e Исключение MethodArgumentNotValidException, которое необходимо обработать.
   * @return Объект ответа ValidationErrorResponse с информацией о нарушениях валидации.
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ResponseBody
  public ValidationErrorResponse onMethodArgumentNotValidException(
      MethodArgumentNotValidException e
  ) {
    log.debug("Обработка MethodArgumentNotValidException: {}", e.getMessage(), e);
    final List<Violation> violations = e.getBindingResult().getFieldErrors().stream()
        .map(error -> new Violation(error.getField(), error.getDefaultMessage()))
        .collect(Collectors.toList());
    return new ValidationErrorResponse(violations);
  }

  @ExceptionHandler(AuthException.class)
  public ResponseEntity<?> auth(AuthException exception) {
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
    return new ResponseEntity<>(exception.getMessage(), headers, exception.getStatus());
  }
}
