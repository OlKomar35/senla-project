package org.senla.komar.spring.repository;

import org.senla.komar.spring.entity.Street;

import java.util.List;

public interface StreetDao extends GenericDao<Long, Street> {

    List<Street> getAll(Integer limit, Integer page);
}
