package org.senla.komar.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.senla.komar.spring.dto.StreetDto;
import org.senla.komar.spring.exception.StreetNotFoundException;
import org.senla.komar.spring.mapper.StreetMapper;
import org.senla.komar.spring.repository.StreetDao;
import org.senla.komar.spring.service.StreetService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class StreetServiceImpl implements StreetService {

    private final StreetDao streetDAO;
    private final StreetMapper streetMapper;

    @Override
    public void createStreet(StreetDto street) {
        streetDAO.create(streetMapper.toStreet(street));
    }

    @Override
    public StreetDto getStreetById(Long id) {
        StreetDto street = streetMapper.toDto(streetDAO.readById(id));
        if (street == null) {
            throw new StreetNotFoundException("Не нашлось улицы с id=" + id);
        }
        return street;
    }

    @Override
    public List<StreetDto> getAllStreets() {
        return streetDAO.getAll()
                .stream()
                .map(streetMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<StreetDto> getAllStreets(Integer limit, Integer page) {
        return streetDAO.getAll(limit, page)
                .stream()
                .map(streetMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteStreetById(Long id) {
        streetDAO.deleteById(id);
    }

    @Override
    public void updateStreetById(Long id, StreetDto newStreet) {
        newStreet.setId(id);
        streetDAO.update(id,streetMapper.toStreet(newStreet));
    }
}
