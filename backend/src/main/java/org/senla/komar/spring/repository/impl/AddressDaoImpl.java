package org.senla.komar.spring.repository.impl;

import jakarta.persistence.EntityGraph;
import jakarta.persistence.TypedQuery;
import lombok.extern.log4j.Log4j2;
import org.senla.komar.spring.entity.Address;
import org.senla.komar.spring.repository.AbstractDao;
import org.senla.komar.spring.repository.AddressDao;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Реализация интерфейса AddressDao. Обеспечивает доступ к данным адресов через взаимодействие с базой данных.
 *
 * @since 1.0
 */
@Log4j2
@Repository
public class AddressDaoImpl extends AbstractDao<Long, Address> implements AddressDao {

  /**
   * Возвращает класс, представляющий сущность Address.
   *
   * @return Класс Address
   */
  @Override
  protected Class<Address> getEntityClass() {
    return Address.class;
  }


  /**
   * Получает список адресов с возможностью пагинации.
   *
   * @param limit Ограничение количества записей на странице
   * @param page  Номер страницы
   * @return Список адресов
   */
  @Override
  public List<Address> getAll(Integer limit,
      Integer page) {
    String jpql = """
        SELECT a FROM Address a ORDER BY a.city.name ASC, a.street.name ASC
        """;
    TypedQuery<Address> query = entityManager.createQuery(jpql, Address.class);
    query.setFirstResult((page - 1) * limit);
    query.setMaxResults(limit);

    log.info("Выполнение запроса в БД...");
    return query.getResultList();
  }

  /**
   * Получает адрес по указанному идентификатору.
   *
   * @param id Идентификатор адреса
   * @return Прочитанный адрес
   */
  @Override
  public Address readById(Long id) {
    EntityGraph<?> graph = entityManager.getEntityGraph("address-entity-graph");
    Map<String, Object> hints = new HashMap<>();
    hints.put("jakarta.persistence.fetch", graph);

    log.info("Выполнение запроса в БД...");
    return entityManager.find(Address.class, id, hints);
  }

  /**
   * Получает номер дома по идентификатору адреса.
   *
   * @param id Идентификатор адреса
   * @return Номер дома
   */
  @Override
  public int getHouseNumberByAddressId(Long id) {
    EntityGraph<?> graph = entityManager.getEntityGraph("address-house-number-entity-graph");
    Map<String, Object> hints = new HashMap<>();
    hints.put("jakarta.persistence.fetch", graph);
    Address address = entityManager.find(Address.class, id, hints);

    log.info("Выполнение запроса в БД...");
    return address.getHouseNumber();
  }

  /**
   * Получает список адресов по имени города с возможностью пагинации.
   *
   * @param cityName Имя города
   * @param limit    Ограничение количества записей на странице
   * @param page     Номер страницы
   * @return Список адресов
   */
  @Override
  public List<Address> getByCityName(String cityName,
      Integer limit,
      Integer page) {
    TypedQuery<Address> query = entityManager.createQuery(
        """
            SELECT a FROM Address a WHERE LOWER(a.city.name) = LOWER(:cityName) ORDER BY a.city.name ASC
            """
        , Address.class);

    query.setParameter("cityName", cityName);
    query.setFirstResult((page - 1) * limit);
    query.setMaxResults(limit);

    log.info("Выполнение запроса в БД...");
    return query.getResultList();
  }

  /**
   * Получает список адресов по имени города и имени улицы с возможностью пагинации.
   *
   * @param cityName   Имя города
   * @param streetName Имя улицы
   * @param limit      Ограничение количества записей на странице
   * @param page       Номер страницы
   * @return Список адресов
   */
  @Override
  public List<Address> getByCityAndStreet(String cityName,
      String streetName,
      Integer limit,
      Integer page) {
    TypedQuery<Address> query = entityManager.createQuery(
        """
            SELECT a FROM Address a WHERE LOWER(a.city.name) = LOWER(:cityName)
            AND LOWER(a.street.name) = LOWER(:streetName) ORDER BY a.street.name ASC
            """,
        Address.class);

    query.setParameter("cityName", cityName);
    query.setParameter("streetName", streetName);
    query.setFirstResult((page - 1) * limit);
    query.setMaxResults(limit);

    log.info("Выполнение запроса в БД...");
    return query.getResultList();
  }
}
