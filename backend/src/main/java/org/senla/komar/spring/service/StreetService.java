package org.senla.komar.spring.service;


import org.senla.komar.spring.dto.StreetDto;

import java.util.List;


public interface StreetService {

    void createStreet(StreetDto street);

    StreetDto getStreetById(Long id);

    List<StreetDto> getAllStreets(Integer limit, Integer page);

    void deleteStreetById(Long id);

    void updateStreetById(Long id, StreetDto newStreet);
}
