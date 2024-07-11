package org.senla.komar.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.senla.komar.spring.dto.StreetDto;
import org.senla.komar.spring.exception.StreetNotFoundException;
import org.senla.komar.spring.mapper.StreetMapper;
import org.senla.komar.spring.repository.StreetRepository;
import org.senla.komar.spring.service.StreetService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class StreetServiceImpl implements StreetService {

  private final StreetRepository streetRepository;
  private final StreetMapper streetMapper;

  @Override
  public void createStreet(StreetDto street) {
    streetRepository.save(streetMapper.toStreet(street));
  }

  @Override
  public StreetDto getStreetById(Long id) {
    return streetRepository.findById(id)
        .map(streetMapper::toDto)
        .orElseThrow(() -> new StreetNotFoundException("Не нашлось улицы с id=" + id));
  }

  @Override
  public List<StreetDto> getAllStreets(Integer limit, Integer page) {
    Pageable pageable = PageRequest.of(page - 1, limit);
    return streetRepository.findAll(pageable)
        .stream()
        .map(streetMapper::toDto)
        .collect(Collectors.toList());
  }

  @Override
  public void deleteStreetById(Long id) {
    streetRepository.deleteById(id);
  }

  @Override
  public void updateStreetById(Long id, StreetDto newStreet) {
    newStreet.setId(id);
    streetRepository.save(streetMapper.toStreet(newStreet));
  }
}
