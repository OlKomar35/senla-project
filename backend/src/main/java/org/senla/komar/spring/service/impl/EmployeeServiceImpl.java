package org.senla.komar.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.senla.komar.spring.dto.EmployeeDto;
import org.senla.komar.spring.enums.JobTitle;
import org.senla.komar.spring.exception.EntityNotFoundException;
import org.senla.komar.spring.mapper.EmployeeMapper;
import org.senla.komar.spring.repository.EmployeeRepository;
import org.senla.komar.spring.service.EmployeeService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final EmployeeMapper employeeMapper;

    @Override
    public void createEmployee(EmployeeDto employeeDto) {
        employeeRepository.save(employeeMapper.toEmployee(employeeDto));
    }

    @Override
    public EmployeeDto getEmployeeById(Long id) {
        return employeeRepository.findById(id)
            .map(employeeMapper::toDto)
            .orElseThrow(() -> new EntityNotFoundException("Не нашлось работника с id=" + id));
    }

    @Override
    public List<EmployeeDto> getAllEmployees(Integer limit, Integer page) {
        Pageable pageable = PageRequest.of(page - 1, limit);
        return employeeRepository.findAll(pageable)
                .stream()
                .map(employeeMapper::toDto)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        employeeRepository.deleteById(id);
    }

    @Override
    public void updateEmployeeById(Long id, EmployeeDto newEmployee) {
        newEmployee.setId(id);
        employeeRepository.save(employeeMapper.toEmployee(newEmployee));

    }

    @Override
    public EmployeeDto getEmployeeByLogin(String login) {
        return employeeRepository.findByPersonLogin(login)
            .map(employeeMapper::toDto)
            .orElseThrow(() -> new EntityNotFoundException("Не нашлось работника с login=" + login));
    }

    @Override
    public List<EmployeeDto> getEmployeesBySurname(String surname,Integer limit,Integer page) {
        Pageable pageable = PageRequest.of(page-1, limit);
        return employeeRepository.findByPersonSurname(surname, pageable)
                .stream()
                .map(employeeMapper::toDto)
                .toList();
    }

    @Override
    public List<EmployeeDto> getEmployeesByJobTitle(JobTitle jobTitle, Integer limit, Integer page) {
        Pageable pageable = PageRequest.of(page-1, limit);
        return employeeRepository.findByJobTitle(jobTitle, pageable)
                .stream()
                .map(employeeMapper::toDto)
                .toList();
    }

    @Override
    public List<EmployeeDto> getEmployeesByStatus(boolean status, Integer limit, Integer page) {
        Pageable pageable = PageRequest.of(page-1, limit);
        return employeeRepository.findByStatus(status,pageable)
                .stream()
                .map(employeeMapper::toDto)
                .toList();
    }

    @Override
    public List<EmployeeDto> getEmployeesByJobTitleWithStatus(JobTitle jobTitle, boolean status, Integer limit, Integer page) {
        Pageable pageable = PageRequest.of(page-1, limit);
        return employeeRepository.findByJobTitleAndStatus(jobTitle,status, pageable)
                .stream()
                .map(employeeMapper::toDto)
                .toList();
    }
}
