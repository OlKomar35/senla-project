package org.senla.komar.spring.mapper;

import org.mapstruct.Mapper;
import org.senla.komar.spring.dto.HotelDtoShortInfo;
import org.senla.komar.spring.entity.Hotel;

@Mapper(componentModel = "spring")
public interface HotelShortInfoMapper {
    Hotel toHotel(HotelDtoShortInfo hotelDtoShortInfo);
    HotelDtoShortInfo toDto(Hotel hotel);

}
