package org.senla.komar.spring.repository;

import org.senla.komar.spring.entity.Person;

import java.util.List;
import java.util.Optional;


public interface PersonDao extends GenericDao<Long, Person> {
    List<Person> getAll(Integer limit, Integer page);

    Person getPersonByPassportData(String passportSeries, int passportNumber);
    Optional<Person> getPersonByPassportDataEntityGraph(String passportSeries, int passportNumber);
    Person getPersonByLogin(String login);
    Person getPersonByEmail(String email);
    boolean existsByUsername(String login);
    boolean existsByEmail(String email);

    Person getPersonByPhoneNumber(String phoneNumber);
}
