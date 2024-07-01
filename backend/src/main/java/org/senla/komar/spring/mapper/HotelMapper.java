package org.senla.komar.spring.mapper;

import org.mapstruct.Mapper;
import org.senla.komar.spring.dto.HotelDto;
import org.senla.komar.spring.entity.Hotel;

@Mapper(componentModel = "spring")
public interface HotelMapper {
    Hotel toHotel(HotelDto hotelDto);
    HotelDto toDto(Hotel hotel);

}
