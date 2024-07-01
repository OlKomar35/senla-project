package org.senla.komar.spring.mapper;

import java.util.List;
import org.mapstruct.Mapper;
import org.senla.komar.spring.dto.CityDto;
import org.senla.komar.spring.entity.City;

@Mapper(componentModel = "spring")
public interface CityMapper {
    City toCity(CityDto cityDto);
    CityDto toDto(City city);

}
