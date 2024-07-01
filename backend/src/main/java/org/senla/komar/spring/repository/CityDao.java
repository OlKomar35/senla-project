package org.senla.komar.spring.repository;

import org.senla.komar.spring.entity.City;

import java.util.List;

public interface CityDao extends GenericDao<Long, City> {

    List<City> getAll(Integer limit, Integer page);
}
