package org.senla.komar.spring.repository;

import java.math.BigDecimal;
import java.util.Optional;
import org.senla.komar.spring.entity.Guest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface GuestRepository extends JpaRepository<Guest, Long> {
    Optional<Guest> findByPersonPhoneNumber(String phoneNumber);
    Page<Guest> findAllByPersonSurname(String surname, Pageable pageable);
    @Query( "SELECT g FROM Guest g WHERE g.id = :id")
    BigDecimal getRankById(Long id);
}
