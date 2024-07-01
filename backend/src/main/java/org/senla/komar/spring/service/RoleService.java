package org.senla.komar.spring.service;

import org.senla.komar.spring.dto.RoleDto;

import java.util.List;

public interface RoleService {
//    void createRole(RoleDto roleDto);
//    RoleDto getRoleById(Long id);
//    List<RoleDto> getAll();
//    void deleteById(Long id);
//    void updateRoleById(Long id, RoleDto newRole);
    RoleDto getRoleByName(String name);
}
