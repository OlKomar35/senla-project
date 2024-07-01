package org.senla.komar.spring.mapper;

import org.mapstruct.Mapper;
import org.senla.komar.spring.dto.RoleDto;
import org.senla.komar.spring.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role toRole(RoleDto roleDt);
    RoleDto toDto(Role role);
}
