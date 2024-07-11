package org.senla.komar.spring.repository;

import org.senla.komar.spring.entity.Street;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StreetRepository extends JpaRepository<Street, Long> {

}
