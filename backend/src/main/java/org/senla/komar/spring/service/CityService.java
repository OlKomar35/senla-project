package org.senla.komar.spring.service;

import org.senla.komar.spring.dto.CityDto;

import java.util.List;

public interface CityService {
    void createCity(CityDto cityDto);

    CityDto getCityById(Long id);

    List<CityDto> getAllCities();

    List<CityDto> getAllCities(Integer limit, Integer page);

    void deleteById(Long id);

    void updateById(Long id, CityDto newCity);
}
