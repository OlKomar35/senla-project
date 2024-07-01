package org.senla.komar.spring.repository.impl;

import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import org.senla.komar.spring.entity.*;
import org.senla.komar.spring.repository.AbstractDao;
import org.senla.komar.spring.repository.GuestDao;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class GuestDaoImpl extends AbstractDao<Long, Guest> implements GuestDao {
    @Override
    protected Class<Guest> getEntityClass() {
        return Guest.class;
    }

    @Override
    public Guest getGuestByPhoneNumber(String phoneNumber) {
        TypedQuery<Guest> query = entityManager.createQuery(
                "SELECT g FROM Guest g WHERE g.person.phoneNumber = :phoneNumber ",
                Guest.class);
        query.setParameter("phoneNumber", phoneNumber);
        return query.getSingleResult();
    }

    @Override
    public List<Guest> getGuestsBySurname(String surname, Integer limit, Integer page) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Guest> criteriaQuery = criteriaBuilder.createQuery(Guest.class);
        Root<Guest> root = criteriaQuery.from(Guest.class);

        root.fetch(Guest_.person, JoinType.INNER);

        Join<Guest, Person> personJoin = root.join(Guest_.person);

        criteriaQuery.select(root)
                .where(criteriaBuilder.equal(personJoin.get(Person_.surname), surname));

        TypedQuery<Guest> typedQuery = entityManager.createQuery(criteriaQuery);
        typedQuery.setFirstResult((page - 1) * limit);
        typedQuery.setMaxResults(limit);
        return typedQuery.getResultList();
    }

    @Override
    public List<Guest> getAll(Integer limit, Integer page){
        String jpql = "SELECT g FROM Guest g ORDER BY g.person.firstname ASC, g.person.surname ASC, g.person.middleName ASC";
        TypedQuery<Guest> query = entityManager.createQuery(jpql, Guest.class);
        query.setFirstResult((page - 1) * limit);
        query.setMaxResults(limit);

        return query.getResultList();
    }

    @Override
    public BigDecimal getRankById(Long id){
        String jpql = "SELECT g FROM Guest g WHERE g.id = :id";
        TypedQuery<Guest> query = entityManager.createQuery(jpql, Guest.class);
        query.setParameter("id", id);
        return query.getSingleResult().getRank();
    }
}
