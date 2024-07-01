package org.senla.komar.spring.mapper;

import org.mapstruct.Mapper;
import org.senla.komar.spring.dto.StreetDto;
import org.senla.komar.spring.entity.Street;

@Mapper(componentModel = "spring")
public interface StreetMapper {
    Street toStreet(StreetDto streetDt);
    StreetDto toDto(Street street);
}
