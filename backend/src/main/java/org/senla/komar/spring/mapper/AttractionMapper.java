package org.senla.komar.spring.mapper;

import org.mapstruct.Mapper;
import org.senla.komar.spring.dto.AttractionDto;
import org.senla.komar.spring.entity.Attraction;

@Mapper(componentModel = "spring")
public interface AttractionMapper {
    Attraction toAttraction(AttractionDto attractionDto);
    AttractionDto toDto(Attraction attraction);
}
