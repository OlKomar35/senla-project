package org.senla.komar.spring.service;

import org.senla.komar.spring.dto.*;
import org.senla.komar.spring.enums.BookingStatus;
import org.springframework.http.ResponseEntity;

import java.util.Date;
import java.util.List;

public interface BookingService {
    void createBooking(BookingDtoFullInfo booking);

    BookingDtoFullInfo getBookingById(AuthPersonDto personDto, Long id);

    List<BookingDtoFullInfo> getAllBooking(Integer limit, Integer page);

    void deleteById(Long id);

    void updateById(Long id, BookingDtoFullInfo newBooking);

    List<BookingDtoFullInfo> getAllBookingsByDateIn(Date date, Integer limit, Integer page);

    List<BookingDtoFullInfo> getAllBookingsByDateOut(Date date, Integer limit, Integer page);

    List<BookingDtoFullInfo> getAllBookingsByStatus(BookingStatus status, Integer limit, Integer page);

    ResponseEntity<?> getAllFacilityByBookingId(AuthPersonDto personDto, Long id);
}
