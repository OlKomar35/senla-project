package org.senla.komar.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.senla.komar.spring.dto.CityDto;
import org.senla.komar.spring.exception.EntityNotFoundException;
import org.senla.komar.spring.mapper.CityMapper;
import org.senla.komar.spring.repository.CityRepository;
import org.senla.komar.spring.service.CityService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    @Override
    public void createCity(CityDto cityDto) {
        cityRepository.save(cityMapper.toCity(cityDto));
    }

    @Override
    public CityDto getCityById(Long id) {
        return cityRepository.findById(id)
            .map(cityMapper::toDto)
            .orElseThrow(() -> new EntityNotFoundException("Не нашлось города с id=" + id));
    }

    @Override
    public List<CityDto> getAllCities() {
        return cityRepository.findAll().stream()
                .map(cityMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<CityDto> getAllCities(Integer limit, Integer page) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        return cityRepository.findAll(pageable).stream()
                .map(cityMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        cityRepository.deleteById(id);
    }

    @Override
    public void updateById(Long id, CityDto newCity) {
        newCity.setId(id);
        cityRepository.save(cityMapper.toCity(newCity));
    }
}
