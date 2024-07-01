package org.senla.komar.spring.repository;

import org.senla.komar.spring.entity.Booking;
import org.senla.komar.spring.entity.Facility;
import org.senla.komar.spring.enums.BookingStatus;

import java.util.Date;
import java.util.List;

public interface BookingDao extends GenericDao<Long, Booking> {
    List<Booking> getAll(Integer limit, Integer page);

    List<Booking> getAllBookingsByDateIn(Date date, Integer limit, Integer page);
    List<Booking> getAllBookingsByDateOut(Date date, Integer limit, Integer page);
    List<Booking> getAllBookingsByStatus(BookingStatus status, Integer limit, Integer page);
    List<Facility> getAllFacilityByBookingId(Long id);
}
