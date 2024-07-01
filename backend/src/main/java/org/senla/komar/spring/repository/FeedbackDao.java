package org.senla.komar.spring.repository;

import org.senla.komar.spring.entity.Feedback;

public interface FeedbackDao extends GenericDao<Long, Feedback> {
    void recalculateHotelRank(Long hotelId);
    void recalculateGuestRank(Long guestId);
}
