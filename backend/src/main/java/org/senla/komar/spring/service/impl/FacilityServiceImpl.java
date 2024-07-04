package org.senla.komar.spring.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.senla.komar.spring.dto.FacilityDto;
import org.senla.komar.spring.exception.EmployeeNotFoundException;
import org.senla.komar.spring.mapper.FacilityMapper;
import org.senla.komar.spring.repository.FacilityRepository;
import org.senla.komar.spring.service.FacilityService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FacilityServiceImpl implements FacilityService {

  private final FacilityRepository facilityRepository;
  private final FacilityMapper facilityMapper;

  @Override
  public void createFacility(FacilityDto facilityDto) {
    facilityRepository.save(facilityMapper.toFacility(facilityDto));
  }

  @Override
  public FacilityDto getFacilityById(Integer id) {
    return facilityRepository.findById(id)
        .map(facilityMapper::toDto)
        .orElseThrow(() -> new EmployeeNotFoundException("Не нашлось работника с id=" + id));
  }

  @Override
  public List<FacilityDto> getAll(Integer limit, Integer page) {
    Pageable pageable = PageRequest.of(page - 1, limit);
    return facilityRepository.findAll(pageable).stream().map(facilityMapper::toDto).toList();
  }

  @Override
  public void deleteById(Integer id) {
    facilityRepository.deleteById(id);
  }

  @Override
  public void updateFacilityById(Integer id, FacilityDto newFacility) {
    newFacility.setId(id);
    facilityRepository.save(facilityMapper.toFacility(newFacility));
  }
}
