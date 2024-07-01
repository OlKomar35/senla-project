package org.senla.komar.spring.service;

import org.senla.komar.spring.dto.AttractionDto;
import org.senla.komar.spring.dto.HotelDtoShortInfo;

import java.util.List;

public interface AttractionService {
    void createAttraction(AttractionDto attractionDto);

    AttractionDto getAttractionById(Long id);

    List<AttractionDto> getAllAttraction();

    void deleteById(Long id);

    void updateById(Long id, AttractionDto newAttraction);

    List<AttractionDto> getAttractionsNearHotel(HotelDtoShortInfo hotel, Integer limit, Integer page);

    List<AttractionDto> getAttractionsByCity(String nameCity, Integer limit, Integer page);

    List<AttractionDto> getAttractionsByCityAndStreet(String nameCity, String nameStreet, Integer limit, Integer page);
}
