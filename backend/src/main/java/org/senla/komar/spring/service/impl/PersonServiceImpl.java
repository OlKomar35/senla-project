package org.senla.komar.spring.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.senla.komar.spring.dto.PermissionDto;
import org.senla.komar.spring.dto.PersonDto;
import org.senla.komar.spring.dto.RoleDto;
import org.senla.komar.spring.entity.Person;
import org.senla.komar.spring.exception.PersonNotFoundException;
import org.senla.komar.spring.mapper.PersonMapper;
import org.senla.komar.spring.repository.PersonRepository;
import org.senla.komar.spring.service.PersonService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Log4j2
@Service
@Transactional
@RequiredArgsConstructor
public class PersonServiceImpl implements PersonService, UserDetailsService {

    private final PersonRepository personRepository;
    private final PersonMapper personMapper;

    public void createPerson(PersonDto person) {
        personRepository.save(personMapper.toPerson(person));
    }

    public PersonDto getPersonById(Long id) {
        return personRepository.findById(id)
            .map(personMapper::toDto)
            .orElseThrow(() -> new PersonNotFoundException("Не нашелся человек с id= " + id));
    }

    public List<PersonDto> getAll(Integer limit, Integer page) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        return personRepository.findAll(pageable).stream()
                .map(personMapper::toDto)
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        if (!personRepository.isExistById(id)) {
            throw new PersonNotFoundException("Ошибка при удалении! Не нашелся человек с id= " + id);
        }
        personRepository.deleteById(id);
    }

    public void updatePersonById(Long id, PersonDto newPerson) {
        newPerson.setId(id);
        personRepository.save(personMapper.toPerson(newPerson));
    }

    @Override
    public PersonDto getPersonByPassportData(String passportSeries, int passportNumber) {
        return personRepository.findByPassportSeriesAndPassportNumber(passportSeries, passportNumber)
            .map(personMapper::toDto)
            .orElseThrow(() ->new PersonNotFoundException("Не нашелся человек с паспортом= " + passportSeries + " " + passportNumber));
    }

    @Override
    public PersonDto getPersonByEmail(String email) {
        return personRepository.findByEmail(email)
            .map(personMapper::toDto)
            .orElseThrow(() -> new PersonNotFoundException("Не нашелся человек с email= " + email));
    }

    @Override
    public PersonDto getPersonByPhoneNumber(String phoneNumber) {
        return personRepository.findByPhoneNumber(phoneNumber)
            .map(personMapper::toDto)
            .orElseThrow(() ->  new PersonNotFoundException("Не нашелся человек с номером телефона= " + phoneNumber));
    }

    @Override
    public PersonDto getPersonByLogin(String login) {
     return personRepository.findByLogin(login)
         .map(personMapper::toDto)
         .orElseThrow(()->new PersonNotFoundException("Не нашелся человек с логином= " + login));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        PersonDto person = getPersonByLogin(username);
        if (person == null) {
            throw new PersonNotFoundException(String.format("Пользователь с логином %s", username));
        }

        List<GrantedAuthority> authorities = new ArrayList<>();

        for (RoleDto role : person.getRoles()) {
            if (role != null) {
                authorities.add(new SimpleGrantedAuthority(role.getName()));
                for (PermissionDto permission : role.getPermissions()) {
                    if (permission != null) {
                        authorities.add(new SimpleGrantedAuthority(permission.getName()));
                    }
                }
            }
        }

        return new User(person.getLogin(), person.getPassword(), authorities);
    }

    @Override
    public boolean existsByUsername(String login) {
        boolean isExist = personRepository.existsByUsername(login);
        log.info("Существует пользователь с логином {}{}", login, String.valueOf(isExist));
        return personRepository.existsByUsername(login);
    }

    @Override
    public boolean existsByEmail(String email) {
        return personRepository.existsByEmail(email);
    }
}
