package org.senla.komar.spring.repository;

import org.senla.komar.spring.entity.Address;

import java.util.List;

/**
 * Интерфейс AddressDao расширяет интерфейс GenericDao для доступа к данным адресов через взаимодействие с базой данных.
 * Предоставляет дополнительные методы для работы с адресами.
 *
 * @since 1.0
 */
public interface AddressDao extends GenericDao<Long, Address> {

  List<Address> getAll(Integer limit, Integer page);

  int getHouseNumberByAddressId(Long id);

  List<Address> getByCityName(String cityName, Integer limit, Integer page);

  List<Address> getByCityAndStreet(String cityName, String streetName, Integer limit, Integer page);
}
