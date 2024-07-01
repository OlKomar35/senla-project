package org.senla.komar.spring.repository;

import org.senla.komar.spring.entity.Role;

public interface RoleDao extends GenericDao<Long, Role> {
    Role findByName(String name);
}
