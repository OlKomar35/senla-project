package org.senla.komar.spring.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.senla.komar.spring.dto.JwtRequest;
import org.senla.komar.spring.dto.PersonDto;
import org.senla.komar.spring.service.security.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Класс контроллера, предоставляющий REST API для аутентификации и авторизации пользователей.
 *
 * <p>Данный контроллер обеспечивает создание токена аутентификации для пользователя,
 * а также регистрацию новых пользователей.
 * </p>
 */
@Log4j2
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class  AuthController {
    private final AuthService authService;

    @PostMapping("/sign-in")
    public ResponseEntity<?> createAuthToken(@RequestBody JwtRequest authRequest){
        return authService.createAuthToken(authRequest);
    }

    @PostMapping("/sign-up")
    public ResponseEntity<?> createNewPerson(@RequestBody PersonDto personDto){
        return authService.createNewPerson(personDto);
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String authorizationHeader) {
        String token = authorizationHeader.substring("Bearer ".length());
        log.info(authorizationHeader);
        log.info(token);

        authService.invalidateToken(token);
        return ResponseEntity.ok().build();
    }

}
