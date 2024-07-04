package org.senla.komar.spring.repository;

import org.senla.komar.spring.entity.Address;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * @author Olga Komar
 * <p>
 * Created at 04.07.2024
 */
public interface AddressRepository extends JpaRepository<Address, Long> {
  Page<Address> findAllByCityName(String name, Pageable pageable);
  Page<Address> findAllByCityNameAndStreetName(String cityName,String streetName, Pageable pageable);
  @Query(" SELECT a.houseNumber FROM Address a WHERE a.id =:id")
  int getHouseNumberByAddressId(Long id);

}
