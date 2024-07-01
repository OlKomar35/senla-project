package org.senla.komar.spring.repository.impl;

import jakarta.persistence.TypedQuery;
import org.senla.komar.spring.entity.City;
import org.senla.komar.spring.repository.AbstractDao;
import org.senla.komar.spring.repository.CityDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CityDaoImpl extends AbstractDao<Long, City> implements CityDao {

  @Override
  protected Class<City> getEntityClass() {
    return City.class;
  }

  @Override
  public List<City> getAll(Integer limit, Integer page) {
    String jpql = """
        SELECT c FROM City c ORDER BY c.name ASC
        """;
    TypedQuery<City> query = entityManager.createQuery(jpql, City.class);
    query.setFirstResult((page - 1) * limit);
    query.setMaxResults(limit);

    return query.getResultList();
  }

}
