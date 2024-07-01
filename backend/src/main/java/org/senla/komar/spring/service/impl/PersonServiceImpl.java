package org.senla.komar.spring.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.senla.komar.spring.dto.PermissionDto;
import org.senla.komar.spring.dto.PersonDto;
import org.senla.komar.spring.dto.RoleDto;
import org.senla.komar.spring.exception.PersonNotFoundException;
import org.senla.komar.spring.mapper.PersonMapper;
import org.senla.komar.spring.repository.PersonDao;
import org.senla.komar.spring.service.PersonService;
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

    private final PersonDao personDAO;
    private final PersonMapper personMapper;

    public void createPerson(PersonDto person) {
        personDAO.create(personMapper.toPerson(person));
    }

    public PersonDto getPersonById(Long id) {
        PersonDto person = personMapper.toDto(personDAO.readById(id));
        if (person == null) {
            throw new PersonNotFoundException("Не нашелся человек с id= " + id);
        }
        return person;
    }

    public List<PersonDto> getAll(Integer limit, Integer page) {
        return personDAO.getAll(limit, page).stream()
                .map(personMapper::toDto)
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        PersonDto person = personMapper.toDto(personDAO.readById(id));
        if (person == null) {
            throw new PersonNotFoundException("Не нашелся человек с id= " + id);
        }
        personDAO.deleteById(id);
    }

    public void updatePersonById(Long id, PersonDto newPerson) {
        newPerson.setId(id);
        personDAO.update(id,personMapper.toPerson(newPerson));
    }

    @Override
    public PersonDto getPersonByPassportData(String passportSeries, int passportNumber) {
        PersonDto person = personMapper.toDto(personDAO.getPersonByPassportData(passportSeries, passportNumber));
        if (person == null) {
            throw new PersonNotFoundException("Не нашелся человек с паспортом= " + passportSeries + " " + passportNumber);
        }
        return person;
    }

    @Override
    public PersonDto getPersonByPassportDataEntityGraph(String passportSeries, int passportNumber) {
        return personDAO.getPersonByPassportDataEntityGraph(passportSeries, passportNumber)
                .map(personMapper::toDto)
                .orElseThrow(() ->
                        new PersonNotFoundException("Не нашелся человек с паспортом= " + passportSeries + " " + passportNumber));
    }

    @Override
    public PersonDto getPersonByEmail(String email) {
        PersonDto person = personMapper.toDto(personDAO.getPersonByEmail(email));
        if (person == null) {
            throw new PersonNotFoundException("Не нашелся человек с email= " + email);
        }
        return person;
    }

    @Override
    public PersonDto getPersonByPhoneNumber(String phoneNumber) {
        PersonDto person = personMapper.toDto(personDAO.getPersonByPhoneNumber(phoneNumber));
        if (person == null) {
            throw new PersonNotFoundException("Не нашелся человек с номером телефона= " + phoneNumber);
        }
        return person;
    }

    @Override
    public PersonDto getPersonByLogin(String login) {
        PersonDto person = personMapper.toDto(personDAO.getPersonByLogin(login));
        if (person == null) {
            throw new PersonNotFoundException("Не нашелся человек с логином= " + login);
        }
        return person;
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
        boolean isExist = personDAO.existsByUsername(login);
        log.info("Существует пользователь с логином {}{}", login, String.valueOf(isExist));
        return personDAO.existsByUsername(login);
    }

    @Override
    public boolean existsByEmail(String email) {
        return personDAO.existsByEmail(email);
    }
}
