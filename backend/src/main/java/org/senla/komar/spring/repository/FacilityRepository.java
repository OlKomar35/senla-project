package org.senla.komar.spring.repository;

import org.senla.komar.spring.entity.Facility;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FacilityRepository extends JpaRepository<Facility, Integer> {

}
