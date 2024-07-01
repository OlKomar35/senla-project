package org.senla.komar.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.senla.komar.spring.dto.RoleDto;
import org.senla.komar.spring.mapper.RoleMapper;
import org.senla.komar.spring.repository.RoleDao;
import org.senla.komar.spring.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleDao roleDao;
    private final RoleMapper roleMapper;

    @Override
    public RoleDto getRoleByName(String name) {
        return roleMapper.toDto(roleDao.findByName(name));
    }
}
