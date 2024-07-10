package org.senla.komar.spring.repository;

import java.math.BigDecimal;
import java.util.List;
import org.senla.komar.spring.entity.Feedback;
import org.senla.komar.spring.entity.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    Page<Hotel> findAllByAddressCity(String cityName, Pageable pageable);

    @Query("SELECT h.feedbacks FROM Hotel h WHERE h.id = :id ")
    List<Feedback> getFeedbacksById(Long id);

    @Query( "SELECT h.rank FROM Hotel h WHERE h.id = :id")
    BigDecimal getRankById(Long id);
}
