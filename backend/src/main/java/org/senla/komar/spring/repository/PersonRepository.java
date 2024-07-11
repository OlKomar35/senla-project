package org.senla.komar.spring.repository;

import java.util.List;
import java.util.Optional;
import org.senla.komar.spring.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByPassportSeriesAndPassportNumber(String passportSeries, int passportNumber);
    Optional<Person> findByLogin(String login);
    Optional<Person> findByEmail(String email);
    Optional<Person> findByPhoneNumber(String phoneNumber);

    @Query("SELECT EXISTS (SELECT p FROM Person p WHERE p.id = :id)")
    boolean isExistById(Long id);
    @Query("SELECT EXISTS (SELECT p FROM Person p WHERE p.login = :login)")
    boolean existsByUsername(String login);
    @Query("SELECT EXISTS (SELECT p FROM Person p WHERE p.email = :email)")
    boolean existsByEmail(String email);
}
