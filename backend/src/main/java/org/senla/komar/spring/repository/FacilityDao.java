package org.senla.komar.spring.repository;

import org.senla.komar.spring.dto.FacilityDto;
import org.senla.komar.spring.entity.Facility;

import java.util.List;

public interface FacilityDao extends GenericDao<Integer, Facility> {
    List<Facility> getAll(Integer limit, Integer page);
}
