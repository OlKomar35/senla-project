package org.senla.komar.spring.mapper;

import org.mapstruct.Mapper;
import org.senla.komar.spring.dto.PersonDto;
import org.senla.komar.spring.entity.Person;

@Mapper(componentModel = "spring")
public interface PersonMapper {
    Person toPerson(PersonDto personDto);
    PersonDto toDto(Person person);
}
