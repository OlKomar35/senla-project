package org.senla.komar.spring.repository.impl;

import jakarta.persistence.TypedQuery;
import java.util.Date;
import java.util.List;
import org.senla.komar.spring.entity.Booking;
import org.senla.komar.spring.entity.Facility;
import org.senla.komar.spring.enums.BookingStatus;
import org.senla.komar.spring.repository.AbstractDao;
import org.senla.komar.spring.repository.BookingDao;
import org.springframework.stereotype.Repository;

@Repository
public class BookingDaoImpl extends AbstractDao<Long, Booking> implements BookingDao {
    @Override
    protected Class<Booking> getEntityClass() {
        return Booking.class;
    }

    @Override
    public List<Booking> getAll(Integer limit, Integer page) {
        String jpql = "SELECT b FROM Booking b ORDER BY b.guest.id ASC, b.bookingStatus ASC ";
        TypedQuery<Booking> query = entityManager.createQuery(jpql, Booking.class);
        query.setFirstResult((page - 1) * limit);
        query.setMaxResults(limit);

        return query.getResultList();
    }

    @Override
    public List<Booking> getAllBookingsByDateIn(Date date, Integer limit, Integer page) {
        String jpql = """
        SELECT b FROM Booking b WHERE b.checkInDate = :date ORDER BY b.guest.id ASC, b.bookingStatus ASC
        """;
        TypedQuery<Booking> query = entityManager.createQuery(jpql, Booking.class);
        query.setParameter("date", date);
        query.setFirstResult((page - 1) * limit);
        query.setMaxResults(limit);

        return query.getResultList();
    }

    @Override
    public List<Booking> getAllBookingsByDateOut(Date date, Integer limit, Integer page) {
        String jpql = """
            SELECT b FROM Booking b WHERE b.checkOutDate = :date ORDER BY b.guest.id ASC, b.bookingStatus ASC
             """;
        TypedQuery<Booking> query = entityManager.createQuery(jpql, Booking.class);
        query.setParameter("date", date);
        query.setFirstResult((page - 1) * limit);
        query.setMaxResults(limit);

        return query.getResultList();
    }

    @Override
    public List<Booking> getAllBookingsByStatus(BookingStatus status, Integer limit, Integer page) {
        String jpql = """
            SELECT b FROM Booking b WHERE b.bookingStatus = :status ORDER BY b.guest.id ASC, b.bookingStatus ASC
             """;
        TypedQuery<Booking> query = entityManager.createQuery(jpql, Booking.class);
        query.setParameter("status", status);
        query.setFirstResult((page - 1) * limit);
        query.setMaxResults(limit);

        return query.getResultList();
    }

    @Override
    public List<Facility> getAllFacilityByBookingId(Long id){
        return readById(id).getFacilities();
    }
}
