package org.senla.komar.spring.service;

import org.senla.komar.spring.dto.PersonDto;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface PersonService {
    void createPerson(PersonDto personDto);

    PersonDto getPersonById(Long id);

    List<PersonDto> getAll(Integer limit, Integer page);

    void deleteById(Long id);

    void updatePersonById(Long id, PersonDto newPerson);

    PersonDto getPersonByPassportData(String passportSeries, int passportNumber);

    PersonDto getPersonByPassportDataEntityGraph(String passportSeries, int passportNumber);

    PersonDto getPersonByEmail(String email);

    PersonDto getPersonByPhoneNumber(String phoneNumber);

    PersonDto getPersonByLogin(String login);

    UserDetails loadUserByUsername(String username);

    boolean existsByUsername(String login);

    boolean existsByEmail(String email);

}
