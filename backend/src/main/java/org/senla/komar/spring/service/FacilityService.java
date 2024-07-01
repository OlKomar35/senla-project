package org.senla.komar.spring.service;

import java.util.List;
import org.senla.komar.spring.dto.FacilityDto;


public interface FacilityService {
     void createFacility(FacilityDto facilityDto);
     FacilityDto getFacilityById(Integer id);
     List<FacilityDto> getAll(Integer limit, Integer page);
     void deleteById(Integer id);
     void updateFacilityById(Integer id, FacilityDto newFacility);
}
