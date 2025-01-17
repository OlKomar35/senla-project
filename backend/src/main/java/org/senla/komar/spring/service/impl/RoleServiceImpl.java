package org.senla.komar.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.senla.komar.spring.dto.RoleDto;
import org.senla.komar.spring.exception.EntityNotFoundException;
import org.senla.komar.spring.mapper.RoleMapper;
import org.senla.komar.spring.repository.RoleRepository;
import org.senla.komar.spring.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleDao;
    private final RoleMapper roleMapper;

    @Override
    public RoleDto getRoleByName(String name) {
        return roleDao.findByName(name)
            .map(roleMapper::toDto)
            .orElseThrow(() -> new EntityNotFoundException("Не найден роль = " + name));
    }
}
