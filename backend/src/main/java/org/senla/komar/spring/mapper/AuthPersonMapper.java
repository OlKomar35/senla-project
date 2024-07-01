package org.senla.komar.spring.mapper;

import org.mapstruct.Mapper;
import org.senla.komar.spring.dto.AuthPersonDto;
import org.senla.komar.spring.dto.PersonDto;

@Mapper(componentModel = "spring")
public interface AuthPersonMapper {
    PersonDto toPersonDto(AuthPersonDto authPersonDto);
    AuthPersonDto toAuthDto(PersonDto personDto);
}
