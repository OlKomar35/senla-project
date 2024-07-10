package org.senla.komar.spring.repository;

import java.util.Date;
import java.util.List;
import org.senla.komar.spring.entity.Booking;
import org.senla.komar.spring.entity.Facility;
import org.senla.komar.spring.enums.BookingStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    Page<Booking> findAllByCheckInDate(Date date, Pageable pageable);
    Page<Booking> findAllByCheckOutDate(Date date,Pageable pageable);
    Page<Booking> findAllByBookingStatus(BookingStatus status,Pageable pageable);
    @Query("SELECT b.facilities FROM Booking b WHERE b.id = :id ")
    List<Facility> getAllFacilityByBookingId(Long id);
}
