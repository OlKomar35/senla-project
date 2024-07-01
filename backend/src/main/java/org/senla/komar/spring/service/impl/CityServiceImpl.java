package org.senla.komar.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.senla.komar.spring.dto.CityDto;
import org.senla.komar.spring.exception.CityNotFoundException;
import org.senla.komar.spring.mapper.CityMapper;
import org.senla.komar.spring.repository.CityDao;
import org.senla.komar.spring.service.CityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityDao cityDAO;
    private final CityMapper cityMapper;

    @Override
    public void createCity(CityDto cityDto) {
        cityDAO.create(cityMapper.toCity(cityDto));
    }

    @Override
    public CityDto getCityById(Long id) {
        CityDto city = cityMapper.toDto(cityDAO.readById(id));
        if (city == null) {
            throw new CityNotFoundException("Не нашлось города с id=" + id);
        }
        return city;
    }

    @Override
    public List<CityDto> getAllCities() {
        return cityDAO.getAll().stream()
                .map(cityMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CityDto> getAllCities(Integer limit, Integer page) {
        return cityDAO.getAll(limit,page).stream()
                .map(cityMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        cityDAO.deleteById(id);
    }

    @Override
    public void updateById(Long id, CityDto newCity) {
        newCity.setId(id);
        cityDAO.update(id,cityMapper.toCity(newCity));
    }
}
