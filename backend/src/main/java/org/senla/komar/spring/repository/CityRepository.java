package org.senla.komar.spring.repository;

import java.util.List;
import org.senla.komar.spring.entity.City;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CityRepository extends JpaRepository<City, Long> {
}
