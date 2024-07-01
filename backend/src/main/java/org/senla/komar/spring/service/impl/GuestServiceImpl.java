package org.senla.komar.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.senla.komar.spring.dto.FeedbackDto;
import org.senla.komar.spring.dto.GuestDto;
import org.senla.komar.spring.exception.GuestNotFoundException;
import org.senla.komar.spring.mapper.GuestMapper;
import org.senla.komar.spring.repository.GuestDao;
import org.senla.komar.spring.service.GuestService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class GuestServiceImpl implements GuestService {

    private final GuestDao guestDAO;
    private final GuestMapper guestMapper;

    public void createGuest(GuestDto guestDto) {
        guestDAO.create(guestMapper.toGuest(guestDto));
    }

    public GuestDto getGuestById(Long id) {
        GuestDto guest = guestMapper.toDto(guestDAO.readById(id));
        if (guest == null) {
            throw new GuestNotFoundException("Не нашлость гостя с id= " + id);
        }
        return guest;
    }

    public List<GuestDto> getAll(Integer limit, Integer page) {
        return guestDAO.getAll(limit, page)
                .stream()
                .map(guestMapper::toDto)
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) {
        guestDAO.deleteById(id);
    }

    public void updateGuestById(Long id, GuestDto newGuest) {
        newGuest.setId(id);
        guestDAO.update(id,guestMapper.toGuest(newGuest));
    }

    @Override
    public GuestDto getGuestByPhoneNumber(String phoneNumber) {
        return guestMapper.toDto(guestDAO.getGuestByPhoneNumber(phoneNumber));
    }

    @Override
    public List<GuestDto> getGuestsBySurname(String surname, Integer limit, Integer page) {
        return guestDAO.getGuestsBySurname(surname, limit, page)
                .stream()
                .map(guestMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public BigDecimal getRankById(Long id){
        return guestDAO.getRankById(id);
    }

    @Override
    public List<FeedbackDto> getFeedbacksById(Long id) {
        return getGuestById(id).getFeedbacks();
    }
}
