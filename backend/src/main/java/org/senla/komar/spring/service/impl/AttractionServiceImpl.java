package org.senla.komar.spring.service.impl;

import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.senla.komar.spring.dto.AttractionDto;
import org.senla.komar.spring.exception.EntityNotFoundException;
import org.senla.komar.spring.mapper.AttractionMapper;
import org.senla.komar.spring.mapper.HotelShortInfoMapper;
import org.senla.komar.spring.repository.AttractionRepository;
import org.senla.komar.spring.service.AttractionService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AttractionServiceImpl implements AttractionService {

    private final AttractionRepository attractionRepository;
    private final AttractionMapper attractionMapper;
    private final HotelShortInfoMapper hotelMapper;

    @Override
    public void createAttraction(AttractionDto attractionDto) {
        attractionRepository.save(attractionMapper.toAttraction(attractionDto));
    }

    @Override
    public AttractionDto getAttractionById(Long id) {
        return attractionRepository.findById(id)
            .map(attractionMapper::toDto)
            .orElseThrow(() -> new EntityNotFoundException("Не нашлось достопримечательности с id=" + id));
    }

    @Override
    public List<AttractionDto> getAllAttraction() {
        return attractionRepository.findAll().stream()
                .map(attractionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        attractionRepository.deleteById(id);
    }

    @Override
    public void updateById(Long id, AttractionDto newAttraction) {
        newAttraction.setId(id);
        attractionRepository.save(attractionMapper.toAttraction(newAttraction));
    }

    @Override
    public List<AttractionDto> getAttractionsByCity(String nameCity, Integer limit, Integer page) {
        Pageable pageable = PageRequest.of(page-1, limit);
        return attractionRepository.findByAddressCityName(nameCity, pageable)
                .stream()
                .map(attractionMapper::toDto)
                .toList();
    }

    @Override
    public List<AttractionDto> getAttractionsByCityAndStreet(String nameCity, String nameStreet, Integer limit, Integer page) {
        Pageable pageable = PageRequest.of(page-1, limit);
        return attractionRepository.findByAddressCityNameAndAddressStreetName(nameCity, nameStreet,pageable)
                .stream()
                .map(attractionMapper::toDto)
                .toList();
    }
}
