package org.senla.komar.spring.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.senla.komar.spring.dto.FacilityDto;
import org.senla.komar.spring.mapper.FacilityMapper;
import org.senla.komar.spring.repository.FacilityDao;
import org.senla.komar.spring.service.FacilityService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FacilityServiceImpl implements FacilityService {

    private final FacilityDao facilityDao;
    private final FacilityMapper facilityMapper;

    @Override
    public void createFacility(FacilityDto facilityDto) {
        facilityDao.create(facilityMapper.toFacility(facilityDto));
    }

    @Override
    public FacilityDto getFacilityById(Integer id) {
        return facilityMapper.toDto(facilityDao.readById(id));
    }

    @Override
    public List<FacilityDto> getAll(Integer limit, Integer page) {
        return facilityDao.getAll(limit, page).stream().map(facilityMapper::toDto).toList();
    }

    @Override
    public void deleteById(Integer id) {
        facilityDao.deleteById(id);
    }

    @Override
    public void updateFacilityById(Integer id, FacilityDto newFacility) {
        newFacility.setId(id);
        facilityDao.update(id,facilityMapper.toFacility(newFacility));
    }
}
