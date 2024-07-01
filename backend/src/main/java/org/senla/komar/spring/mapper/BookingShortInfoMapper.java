package org.senla.komar.spring.mapper;

import org.mapstruct.Mapper;
import org.senla.komar.spring.dto.BookingDtoShortInfo;
import org.senla.komar.spring.entity.Booking;

@Mapper(componentModel = "spring")
public interface BookingShortInfoMapper {
    Booking toBooking(BookingDtoShortInfo bookingDto);
    BookingDtoShortInfo toDtoShort(Booking booking);
}
