package org.senla.komar.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.senla.komar.spring.dto.AttractionDto;
import org.senla.komar.spring.dto.HotelDtoShortInfo;
import org.senla.komar.spring.mapper.AttractionMapper;
import org.senla.komar.spring.mapper.HotelShortInfoMapper;
import org.senla.komar.spring.repository.AttractionDao;
import org.senla.komar.spring.service.AttractionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class AttractionServiceImpl implements AttractionService {

    private final AttractionDao attractionDAO;
    private final AttractionMapper attractionMapper;
    private final HotelShortInfoMapper hotelMapper;

    @Override
    public void createAttraction(AttractionDto attractionDto) {
        attractionDAO.create(attractionMapper.toAttraction(attractionDto));
    }

    @Override
    public AttractionDto getAttractionById(Long id) {
        return attractionMapper.toDto(attractionDAO.readById(id));
    }

    @Override
    public List<AttractionDto> getAllAttraction() {
        return attractionDAO.getAll().stream()
                .map(attractionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        attractionDAO.deleteById(id);
    }

    @Override
    public void updateById(Long id, AttractionDto newAttraction) {
        newAttraction.setId(id);
        attractionDAO.update(id,attractionMapper.toAttraction(newAttraction));
    }

    @Override
    public List<AttractionDto> getAttractionsNearHotel(HotelDtoShortInfo hotel, Integer limit, Integer page) {
        return attractionDAO.getAttractionsNearHotel(hotelMapper.toHotel(hotel), limit, page)
                .stream()
                .map(attractionMapper::toDto)
                .toList();
    }

    @Override
    public List<AttractionDto> getAttractionsByCity(String nameCity, Integer limit, Integer page) {
        return attractionDAO.getByAttractionsName(nameCity, limit, page)
                .stream()
                .map(attractionMapper::toDto)
                .toList();
    }

    @Override
    public List<AttractionDto> getAttractionsByCityAndStreet(String nameCity, String nameStreet, Integer limit, Integer page) {
        return attractionDAO.getByAttractionsAndStreet(nameCity, nameStreet, limit, page)
                .stream()
                .map(attractionMapper::toDto)
                .toList();
    }
}
