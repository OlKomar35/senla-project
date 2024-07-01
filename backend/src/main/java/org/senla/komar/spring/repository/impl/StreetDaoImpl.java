package org.senla.komar.spring.repository.impl;

import jakarta.persistence.TypedQuery;
import org.senla.komar.spring.entity.Street;
import org.senla.komar.spring.repository.AbstractDao;
import org.senla.komar.spring.repository.StreetDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StreetDaoImpl extends AbstractDao<Long, Street> implements StreetDao {
    @Override
    protected Class<Street> getEntityClass() {
        return Street.class;
    }

    @Override
    public List<Street> getAll(Integer limit, Integer page){
        String jpql = "SELECT s FROM Street s ORDER BY s.name ASC";
        TypedQuery<Street> query = entityManager.createQuery(jpql, Street.class);
        query.setFirstResult((page - 1) * limit);
        query.setMaxResults(limit);

        return query.getResultList();
    }

}
