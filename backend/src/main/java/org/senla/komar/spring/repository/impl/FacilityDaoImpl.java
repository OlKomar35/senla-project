package org.senla.komar.spring.repository.impl;

import jakarta.persistence.TypedQuery;
import java.util.List;
import org.senla.komar.spring.entity.Facility;
import org.senla.komar.spring.repository.AbstractDao;
import org.senla.komar.spring.repository.FacilityDao;
import org.springframework.stereotype.Repository;

@Repository
public class FacilityDaoImpl extends AbstractDao<Integer, Facility> implements FacilityDao {
    @Override
    protected Class<Facility> getEntityClass() {
        return Facility.class;
    }

    @Override
    public List<Facility> getAll(Integer limit, Integer page) {
        String jpql = "SELECT f FROM Facility f ORDER BY f.name ASC";
        TypedQuery<Facility> query = entityManager.createQuery(jpql, Facility.class);
        query.setFirstResult((page - 1) * limit);
        query.setMaxResults(limit);

        return query.getResultList();
    }
}
