package org.senla.komar.spring.repository.impl;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.senla.komar.spring.entity.*;
import org.senla.komar.spring.enums.JobTitle;
import org.senla.komar.spring.repository.AbstractDao;
import org.senla.komar.spring.repository.EmployeeDao;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class EmployeeDaoImpl extends AbstractDao<Long, Employee> implements EmployeeDao {

  @Override
  protected Class<Employee> getEntityClass() {
    return Employee.class;
  }

  @Override
  public Employee getEmployeeByLogin(String login) {
    TypedQuery<Employee> query = entityManager.createQuery(
        """
            SELECT e FROM Employee e WHERE e.person.login = :login
            """, Employee.class);
    query.setParameter("login", login);
    return query.getSingleResult();
  }

  @Override
  public List<Employee> getEmployeesBySurname(String surname, Integer limit, Integer page) {
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
    CriteriaQuery<Employee> criteriaQuery = criteriaBuilder.createQuery(Employee.class);
    Root<Employee> root = criteriaQuery.from(Employee.class);

    root.fetch(Employee_.person, JoinType.INNER);

    Join<Employee, Person> personJoin = root.join(Employee_.person);

    criteriaQuery.select(root)
        .where(criteriaBuilder.equal(personJoin.get(Person_.surname), surname));

    TypedQuery<Employee> typedQuery = entityManager.createQuery(criteriaQuery);
    typedQuery.setFirstResult((page - 1) * limit);
    typedQuery.setMaxResults(limit);

    return typedQuery.getResultList();
  }

  @Override
  public List<Employee> getEmployeesByJobTitle(JobTitle jobTitle, Integer limit, Integer page) {
    TypedQuery<Employee> query = entityManager.createQuery(
        "SELECT e FROM Employee e WHERE e.jobTitle = :jobTitle ", Employee.class);
    query.setParameter("jobTitle", jobTitle);
    query.setFirstResult((page - 1) * limit);
    query.setMaxResults(limit);

    return query.getResultList();
  }

  @Override
  public List<Employee> getEmployeesByStatus(boolean status, Integer limit, Integer page) {
    TypedQuery<Employee> query = entityManager.createQuery(
        "SELECT e FROM Employee e WHERE e.status = :status ", Employee.class);
    query.setParameter("status", status);
    query.setFirstResult((page - 1) * limit);
    query.setMaxResults(limit);

    return query.getResultList();
  }

  @Override
  public List<Employee> getEmployeesByJobTitleWithStatus(JobTitle jobTitle, boolean status, Integer limit,
      Integer page) {
    TypedQuery<Employee> query = entityManager.createQuery(
        "SELECT e FROM Employee e WHERE e.status = :status AND e.jobTitle = :jobTitle", Employee.class);
    query.setParameter("status", status);
    query.setParameter("jobTitle", jobTitle);
    query.setFirstResult((page - 1) * limit);
    query.setMaxResults(limit);

    return query.getResultList();
  }

  @Override
  public List<Employee> getAll(Integer limit, Integer page) {
    String jpql = "SELECT e FROM Employee e ORDER BY e.person.surname ASC, e.person.firstname ASC, e.person.middleName ASC ";
    TypedQuery<Employee> query = entityManager.createQuery(jpql, Employee.class);
    query.setFirstResult((page - 1) * limit);
    query.setMaxResults(limit);

    return query.getResultList();
  }
}
