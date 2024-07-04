package org.senla.komar.spring.repository;

import org.senla.komar.spring.entity.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    void recalculateHotelRank(Long hotelId);
    void recalculateGuestRank(Long guestId);
}
