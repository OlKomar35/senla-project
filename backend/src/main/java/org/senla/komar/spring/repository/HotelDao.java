package org.senla.komar.spring.repository;

import org.senla.komar.spring.entity.Feedback;
import org.senla.komar.spring.entity.Hotel;

import java.math.BigDecimal;
import java.util.List;

public interface HotelDao extends GenericDao<Long, Hotel> {
    List<Hotel> getHotelsByCity(String cityName);
    List<Feedback> getFeedbacksById(Long id);
    BigDecimal getRankById(Long id);
}
