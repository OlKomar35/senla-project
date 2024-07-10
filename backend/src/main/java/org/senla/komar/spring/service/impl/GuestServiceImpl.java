package org.senla.komar.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.senla.komar.spring.dto.FeedbackDto;
import org.senla.komar.spring.dto.GuestDto;
import org.senla.komar.spring.exception.EmployeeNotFoundException;
import org.senla.komar.spring.exception.GuestNotFoundException;
import org.senla.komar.spring.mapper.GuestMapper;
import org.senla.komar.spring.repository.GuestRepository;
import org.senla.komar.spring.service.GuestService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class GuestServiceImpl implements GuestService {

  private final GuestRepository guestRepository;
  private final GuestMapper guestMapper;

  public void createGuest(GuestDto guestDto) {
    guestRepository.save(guestMapper.toGuest(guestDto));
  }

  public GuestDto getGuestById(Long id) {
    return guestRepository.findById(id)
        .map(guestMapper::toDto)
        .orElseThrow(() -> new GuestNotFoundException("Не нашлость гостя с id= " + id));
  }

  public List<GuestDto> getAll(Integer limit, Integer page) {
    Pageable pageable = PageRequest.of(page - 1, limit);
    return guestRepository.findAll(pageable)
        .stream()
        .map(guestMapper::toDto)
        .collect(Collectors.toList());
  }

  public void deleteById(Long id) {
    guestRepository.deleteById(id);
  }

  public void updateGuestById(Long id, GuestDto newGuest) {
    newGuest.setId(id);
    guestRepository.save(guestMapper.toGuest(newGuest));
  }

  @Override
  public GuestDto getGuestByPhoneNumber(String phoneNumber) {
    return guestRepository.findByPersonPhoneNumber(phoneNumber)
        .map(guestMapper::toDto)
        .orElseThrow(() -> new GuestNotFoundException("Не нашлось гостя с номером телефона= " + phoneNumber));
  }

  @Override
  public List<GuestDto> getGuestsBySurname(String surname, Integer limit, Integer page) {
    Pageable pageable = PageRequest.of(page - 1, limit);
    return guestRepository.findAllByPersonSurname(surname, pageable)
        .stream()
        .map(guestMapper::toDto)
        .collect(Collectors.toList());
  }

  @Override
  public BigDecimal getRankById(Long id) {
    return guestRepository.getRankById(id);
  }

  @Override
  public List<FeedbackDto> getFeedbacksById(Long id) {
    return getGuestById(id).getFeedbacks();
  }
}
