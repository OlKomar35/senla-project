package org.senla.komar.spring.repository.impl;

import jakarta.persistence.TypedQuery;
import lombok.extern.log4j.Log4j2;
import org.senla.komar.spring.entity.Attraction;
import org.senla.komar.spring.entity.Hotel;
import org.senla.komar.spring.repository.AbstractDao;
import org.senla.komar.spring.repository.AttractionDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Log4j2
@Repository
public class AttractionDaoImpl extends AbstractDao<Long, Attraction> implements AttractionDao {

  @Override
  protected Class<Attraction> getEntityClass() {
    return Attraction.class;
  }

  @Override
  public List<Attraction> getAll(Integer limit, Integer page) {
    String jpql = """
        SELECT a FROM Attraction a ORDER BY a.name ASC
        """;
    TypedQuery<Attraction> query = entityManager.createQuery(jpql, Attraction.class);
    query.setFirstResult((page - 1) * limit);
    query.setMaxResults(limit);

    log.info("Выполнение запроса в БД...");
    return query.getResultList();
  }

  @Override
  public List<Attraction> getAttractionsNearHotel(Hotel hotel, Integer limit, Integer page) {
    String jpql =
        """
            SELECT a FROM Attraction a WHERE a.address.city = :city
            ORDER BY calculateDistance(:hotelLat, :hotelLon, a.address.latitude, a.address.longitude) ASC
            """;

    TypedQuery<Attraction> query = entityManager.createQuery(jpql, Attraction.class);
    query.setParameter("city", hotel.getAddress().getCity());
    query.setParameter("hotelLat", hotel.getAddress().getLatitude());
    query.setParameter("hotelLon", hotel.getAddress().getLongitude());

    if (limit != null && limit > 0) {
      query.setMaxResults(limit);
    }

    if (page != null && page > 0 && limit != null && limit > 0) {
      int offset = (page - 1) * limit;
      query.setFirstResult(offset);
    }

    log.info("Выполнение запроса в БД...");
    return query.getResultList();
  }

  @Override
  public List<Attraction> getByAttractionsName(String cityName, Integer limit, Integer page) {
    TypedQuery<Attraction> query = entityManager.createQuery(
        """
            SELECT a FROM Attraction a WHERE LOWER(a.address.city.name) = LOWER(:cityName)
            ORDER BY a.address.city.name ASC
            """,
        Attraction.class);
    query.setParameter("cityName", cityName);
    query.setFirstResult((page - 1) * limit);
    query.setMaxResults(limit);

    log.info("Выполнение запроса в БД...");
    return query.getResultList();
  }

  @Override
  public List<Attraction> getByAttractionsAndStreet(String cityName, String streetName, Integer limit, Integer page) {
    TypedQuery<Attraction> query = entityManager.createQuery(
        """
            SELECT a FROM Attraction a WHERE a.address.city.name = LOWER(:cityName)
            AND LOWER(a.address.street.name) = LOWER(:streetName) ORDER BY a.address.street.name ASC
            """,
        Attraction.class);
    query.setParameter("cityName", cityName);
    query.setParameter("streetName", streetName);
    query.setFirstResult((page - 1) * limit);
    query.setMaxResults(limit);

    log.info("Выполнение запроса в БД...");
    return query.getResultList();
  }
}
