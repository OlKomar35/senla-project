package org.senla.komar.spring.mapper;

import org.mapstruct.Mapper;
import org.senla.komar.spring.dto.FacilityDto;
import org.senla.komar.spring.entity.Facility;

@Mapper(componentModel = "spring")
public interface FacilityMapper {
    Facility toFacility(FacilityDto facilityDto);
    FacilityDto toDto(Facility facility);
}
