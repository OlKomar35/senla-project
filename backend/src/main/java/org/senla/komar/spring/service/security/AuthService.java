package org.senla.komar.spring.service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.senla.komar.spring.dto.*;
import org.senla.komar.spring.mapper.AuthPersonMapper;
import org.senla.komar.spring.security.exception.AuthException;
import org.senla.komar.spring.security.utils.JwtTokenUtils;
import org.senla.komar.spring.service.PersonService;
import org.senla.komar.spring.service.RoleService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Log4j2
public class AuthService {

  @Value("${jwt.token.secret}")
  private String secret;

  private final PersonService personService;
  private final RoleService roleService;
  private final JwtTokenUtils jwtTokenUtils;
  private final AuthenticationManager authenticationManager;
  private final PasswordEncoder passwordEncoder;
  private final AuthPersonMapper authPersonMapper;

  public ResponseEntity<?> createAuthToken(JwtRequest authRequest) {
    log.info("Создаем токин для пользователя");
    try {
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(authRequest.getLogin(), authRequest.getPassword()));
    } catch (BadCredentialsException exception) {
      log.debug("Неверный логин или пароль");
      return new ResponseEntity<>(
          new AuthException(HttpStatus.UNAUTHORIZED, "Неправильный логин или пароль"),
          HttpStatus.UNAUTHORIZED);
    }

    UserDetails userDetails = personService.loadUserByUsername(authRequest.getLogin());
    String token = jwtTokenUtils.generateToken(userDetails);
    log.info("Создание токина прошло успешно");

    Authentication authentication = new PreAuthenticatedAuthenticationToken(userDetails, null,
                                                                            userDetails.getAuthorities());
    SecurityContextHolder.getContext().setAuthentication(authentication);

    return ResponseEntity.ok(new JwtResponse(token));
  }

  public ResponseEntity<?> createNewPerson(PersonDto personDto) {
    log.info("Создаем нового пользователя...");
    if (!personDto.getPassword().equals(personDto.getConfirmPassword())) {
      log.debug("Пароли не совпадаю");
      return new ResponseEntity<>(
          new AuthException(HttpStatus.BAD_REQUEST, "Пароли не совпадаю"),
          HttpStatus.BAD_REQUEST);
    }
    log.info("Пароль введен верно, продолжаем создание...");

    if (personService.existsByUsername(personDto.getLogin())) {
      log.debug("Пользователь с указанным именем уже существует");
      return new ResponseEntity<>(
          new AuthException(HttpStatus.BAD_REQUEST, "Пользователь с указанным именем уже существует"),
          HttpStatus.BAD_REQUEST);
    }

    personDto.setPassword(passwordEncoder.encode(personDto.getPassword()));
    RoleDto roleUser = roleService.getRoleByName("ROLE_USER");
    Set<RoleDto> roleSet = new HashSet<>();
    roleSet.add(roleUser);
    personDto.setRoles(roleSet);
    personService.createPerson(personDto);
    log.info("Пользователь создан успешно");

    AuthPersonDto registeredPerson = authPersonMapper.toAuthDto(personDto);

    return ResponseEntity.ok(registeredPerson);
  }

  public void invalidateToken(String token) {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    log.info("Logout: {}",authentication.getPrincipal());

    if (authentication.getPrincipal() != null
        && authentication.getPrincipal().toString().equals(jwtTokenUtils.getLoginFromToken(token))) {
      SecurityContextHolder.clearContext();
    }
  }

}
