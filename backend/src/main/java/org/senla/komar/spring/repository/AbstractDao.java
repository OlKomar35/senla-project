package org.senla.komar.spring.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaQuery;
import lombok.extern.log4j.Log4j2;
import org.senla.komar.spring.exception.EntityIllegalArgumentException;

import java.util.List;

/**
 * Абстрактный класс AbstractDao представляет базовую реализацию DAO (Data Access Object) для работы с сущностями.
 *
 * @param <K> тип ключа сущности (наследник Number)
 * @param <T> тип сущности
 */

@Log4j2
public abstract class AbstractDao<K extends Number, T> {

  /**
   * Контекст хранения EntityManager для доступа к базе данных.
   */
  @PersistenceContext
  protected EntityManager entityManager;

  /**
   * Возвращает класс сущности.
   *
   * @return класс сущности
   */
  protected abstract Class<T> getEntityClass();

  /**
   * Конструктор по умолчанию.
   */
  protected AbstractDao() {
  }

  /**
   * Создает новую сущность.
   *
   * @param t сущность для создания
   */
  public void create(T t) {
    entityManager.persist(t);
    log.info("entity {} has been created", getEntityClass().getSimpleName());
  }

  /**
   * Возвращает сущность по ее идентификатору.
   *
   * @param id идентификатор сущности
   * @return найденная сущность
   * @throws EntityIllegalArgumentException если сущность с указанным идентификатором не найдена
   */
  public T readById(K id) {
    T entity = null;
    try {
      entity = entityManager.find(getEntityClass(), id);
      log.info("entity {} has been read", getEntityClass().getSimpleName());
    } catch (IllegalArgumentException e) {
      throw new EntityIllegalArgumentException(getEntityClass().getSimpleName(), id);
    }
    return entity;
  }

  /**
   * Обновляет сущность с указанным идентификатором.
   *
   * @param id идентификатор сущности
   * @param t  обновленная сущность
   * @return обновленная сущность
   * @throws EntityIllegalArgumentException если сущность с указанным идентификатором не найдена
   */
  public T update(K id, T t) {
    T entity = null;
    try {
      entity = entityManager.merge(t);
      log.info("entity {} has been updated", getEntityClass().getSimpleName());
    } catch (IllegalArgumentException e) {
      throw new EntityIllegalArgumentException(getEntityClass().getSimpleName(), id);
    }
    return entity;
  }

  /**
   * Удаляет сущность по ее идентификатору.
   *
   * @param id идентификатор сущности
   * @throws EntityIllegalArgumentException если сущность с указанным идентификатором не найдена
   */
  public void deleteById(K id) {
    try {
      entityManager.remove(readById(id));
      log.info("entity {} has been deleted", getEntityClass().getSimpleName());
    } catch (IllegalArgumentException e) {
      throw new EntityIllegalArgumentException(getEntityClass().getSimpleName(), id);
    }
  }

  /**
   * Возвращает список всех сущностей.
   *
   * @return список всех сущностей
   */
  public List<T> getAll() {
    CriteriaQuery<T> criteriaQuery = entityManager.getCriteriaBuilder().createQuery(getEntityClass());
    criteriaQuery.from(getEntityClass());
    return entityManager.createQuery(criteriaQuery).getResultList();
  }
}
