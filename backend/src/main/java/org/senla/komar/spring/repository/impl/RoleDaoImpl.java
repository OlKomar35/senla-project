package org.senla.komar.spring.repository.impl;

import jakarta.persistence.TypedQuery;
import org.senla.komar.spring.entity.Role;
import org.senla.komar.spring.repository.AbstractDao;
import org.senla.komar.spring.repository.RoleDao;
import org.springframework.stereotype.Repository;

@Repository
public class RoleDaoImpl extends AbstractDao<Long, Role> implements RoleDao {
    @Override
    protected Class<Role> getEntityClass() {
        return Role.class;
    }

    @Override
    public Role findByName(String name) {
        TypedQuery<Role> query = entityManager.createQuery(
                "SELECT r FROM Role r  WHERE r.name = :name",
                Role.class);
        query.setParameter("name", name);
        return query.getSingleResult();
    }
}
