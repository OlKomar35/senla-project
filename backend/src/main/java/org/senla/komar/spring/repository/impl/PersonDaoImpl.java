package org.senla.komar.spring.repository.impl;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import lombok.extern.log4j.Log4j2;
import org.senla.komar.spring.entity.Person;
import org.senla.komar.spring.repository.AbstractDao;
import org.senla.komar.spring.repository.PersonDao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Log4j2
@Repository
public class PersonDaoImpl extends AbstractDao<Long, Person> implements PersonDao {
    @Override
    protected Class<Person> getEntityClass() {
        return Person.class;
    }

    @Override
    public List<Person> getAll(Integer limit, Integer page){
        String jpql = "SELECT p FROM Person p ORDER BY p.surname ASC ,p.firstname ASC, p.middleName ASC";
        TypedQuery<Person> query = entityManager.createQuery(jpql, Person.class);
        query.setFirstResult((page - 1) * limit);
        query.setMaxResults(limit);

        return query.getResultList();
    }

    @Override
    public Person getPersonByPassportData(String passportSeries, int passportNumber) {
        TypedQuery<Person> query = entityManager.createQuery(
                "SELECT p FROM Person p  WHERE p.passportSeries = :passportSeries AND p.passportNumber = :passportNumber",
                Person.class);
        query.setParameter("passportSeries", passportSeries);
        query.setParameter("passportNumber", passportNumber);
        return query.getSingleResult();
    }

    public Optional<Person> getPersonByPassportDataEntityGraph(String passportSeries, int passportNumber) {
        EntityGraph<?> graph = entityManager.getEntityGraph("persson-entity-graph");
        Map<String, Object> hints = new HashMap<>();
        hints.put("jakarta.persistence.fetchgraph", graph);

        return Optional.ofNullable((Person) entityManager
                .createQuery("SELECT p FROM Person p  WHERE p.passportSeries = :passportSeries AND p.passportNumber = :passportNumber")
                .setParameter("passportSeries", passportSeries)
                .setParameter("passportNumber", passportNumber)
                .setHint("jakarta.persistence.fetchgraph", graph)
                .getSingleResult());
    }

    @Override
    public Person getPersonByLogin(String login) {
        TypedQuery<Person> query = entityManager.createQuery(
                "SELECT p FROM Person p  WHERE p.login = :login", Person.class);
        query.setParameter("login", login);
        Person person;
        try {
            person = query.getSingleResult();
        } catch (NoResultException ex) {
            return null;
        }
        return person;
    }

    @Override
    public Person getPersonByEmail(String email) {
        EntityGraph<?> graph = entityManager.getEntityGraph("persson-entity-graph");
        Map<String, Object> hints = new HashMap<>();
        hints.put("jakarta.persistence.fetchgraph", graph);

        return (Person) entityManager
                .createQuery("SELECT p FROM Person p  WHERE p.email = :email")
                .setParameter("email", email)
                .setHint("jakarta.persistence.fetchgraph", graph)
                .getSingleResult();
    }

    @Override
    public boolean existsByUsername(String login) {
        return getPersonByLogin(login) != null;
    }

    @Override
    public boolean existsByEmail(String email) {
        return getPersonByEmail(email) != null;
    }

    @Override
    public Person getPersonByPhoneNumber(String phoneNumber) {
        EntityGraph<?> graph = entityManager.getEntityGraph("persson-entity-graph");
        Map<String, Object> hints = new HashMap<>();
        hints.put("jakarta.persistence.fetchgraph", graph);

        return (Person) entityManager
                .createQuery("SELECT p FROM Person p  WHERE p.phoneNumber = :phoneNumber")
                .setParameter("phoneNumber", phoneNumber)
                .setHint("jakarta.persistence.fetchgraph", graph);
    }
}
