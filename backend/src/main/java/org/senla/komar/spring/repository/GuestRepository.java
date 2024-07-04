package org.senla.komar.spring.repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import org.senla.komar.spring.entity.Guest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestRepository extends JpaRepository<Guest, Long> {
    Optional<Guest> findByPersonPhoneNumber(String phoneNumber);
    Page<Guest> findByPersonSurname(String surname, Pageable pageable);
    BigDecimal getRankById(Long id);
}
