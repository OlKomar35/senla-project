package org.senla.komar.spring.repository;

import org.senla.komar.spring.entity.Attraction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttractionRepository extends JpaRepository<Attraction, Long> {

    Page<Attraction> findByAddressCityName(String cityName, Pageable pageable);

    Page<Attraction> findByAddressCityNameAndAddressStreetName(String cityName, String streetName, Pageable pageable);
}
