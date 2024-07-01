package org.senla.komar.spring.mapper;

import org.mapstruct.Mapper;
import org.senla.komar.spring.dto.HotelDtoFullInfo;
import org.senla.komar.spring.entity.Hotel;

@Mapper(componentModel = "spring")
public interface HotelFullInfoMapper {
    Hotel toHotel(HotelDtoFullInfo hotelDtoFullInfo);
    HotelDtoFullInfo toDto(Hotel hotel);
}
