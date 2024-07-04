package org.senla.komar.spring.repository;

import java.math.BigDecimal;
import java.util.List;
import org.senla.komar.spring.entity.Feedback;
import org.senla.komar.spring.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long> {
    List<Hotel> findByAddressCityName(String cityName);
    List<Feedback> getFeedbacksById(Long id);
    BigDecimal getRankById(Long id);
}
