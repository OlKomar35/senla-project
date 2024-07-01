package org.senla.komar.spring.service;

import java.math.BigDecimal;
import java.util.List;
import org.senla.komar.spring.dto.FeedbackDto;
import org.senla.komar.spring.dto.GuestDto;


public interface GuestService {
    void createGuest(GuestDto guestDto);
    GuestDto getGuestById(Long id);
    List<GuestDto> getAll(Integer limit, Integer page);
    void deleteById(Long id);
    void updateGuestById(Long id, GuestDto newGuest);
    GuestDto getGuestByPhoneNumber(String phoneNumber);
    List<GuestDto> getGuestsBySurname(String surname, Integer limit, Integer page);

    BigDecimal getRankById(Long id);

    List<FeedbackDto> getFeedbacksById(Long id);
}
