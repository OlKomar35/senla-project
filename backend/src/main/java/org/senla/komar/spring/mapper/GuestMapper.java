package org.senla.komar.spring.mapper;

import org.mapstruct.Mapper;
import org.senla.komar.spring.dto.GuestDto;
import org.senla.komar.spring.entity.Guest;

@Mapper(componentModel = "spring")
public interface GuestMapper {
    Guest toGuest(GuestDto guestDto);
    GuestDto toDto(Guest guest);
}
