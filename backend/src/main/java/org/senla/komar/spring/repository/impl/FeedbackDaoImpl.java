package org.senla.komar.spring.repository.impl;

import lombok.extern.log4j.Log4j2;
import org.senla.komar.spring.entity.Feedback;
import org.senla.komar.spring.repository.AbstractDao;
import org.senla.komar.spring.repository.FeedbackDao;
import org.springframework.stereotype.Repository;

@Log4j2
@Repository
public class FeedbackDaoImpl extends AbstractDao<Long, Feedback> implements FeedbackDao {

    @Override
    protected Class<Feedback> getEntityClass() {
        return Feedback.class;
    }

    @Override
    public void recalculateHotelRank(Long hotelId) {

        Double newRank = entityManager.createQuery(
                        """
                                SELECT AVG(f.score) FROM Hotel h JOIN h.feedbacks f WHERE h.id = :hotelId"""
                        , Double.class)
                .setParameter("hotelId", hotelId)
                .getSingleResult();

        entityManager.createQuery("UPDATE Hotel h SET h.rank = :newRank WHERE h.id = :hotelId")
                .setParameter("newRank", newRank)
                .setParameter("hotelId", hotelId)
                .executeUpdate();

        log.info("Пересчитан рейтинг гостиницы: " + hotelId);
    }

    @Override
    public void recalculateGuestRank(Long guestId) {

        Double newRank = entityManager.createQuery(
                        """
                                SELECT AVG(f.score) FROM Guest g JOIN g.feedbacks f WHERE g.id = :guestId"""
                        , Double.class)
                .setParameter("guestId", guestId)
                .getSingleResult();

        entityManager.createQuery("UPDATE Guest g SET g.rank = :newRank WHERE g.id = :guestId")
                .setParameter("newRank", newRank)
                .setParameter("guestId", guestId)
                .executeUpdate();

        log.info("Пересчитан рейтинг гостя: " + guestId);
    }


}
