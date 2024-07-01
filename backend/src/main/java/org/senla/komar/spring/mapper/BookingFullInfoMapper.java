package org.senla.komar.spring.mapper;

import org.mapstruct.Mapper;
import org.senla.komar.spring.dto.BookingDtoFullInfo;
import org.senla.komar.spring.entity.Booking;

@Mapper(componentModel = "spring")
public interface BookingFullInfoMapper {
    Booking toBooking(BookingDtoFullInfo bookingDto);
    BookingDtoFullInfo toDtoFull(Booking booking);
}
