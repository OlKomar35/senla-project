package org.senla.komar.spring.service.impl;

import lombok.RequiredArgsConstructor;
import org.senla.komar.spring.dto.EmployeeDto;
import org.senla.komar.spring.enums.JobTitle;
import org.senla.komar.spring.exception.EmployeeNotFoundException;
import org.senla.komar.spring.mapper.EmployeeMapper;
import org.senla.komar.spring.repository.EmployeeDao;
import org.senla.komar.spring.service.EmployeeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeDao employeeDao;
    private final EmployeeMapper employeeMapper;

    @Override
    public void createEmployee(EmployeeDto employeeDto) {
        employeeDao.create(employeeMapper.toEmployee(employeeDto));
    }

    @Override
    public EmployeeDto getEmployeeById(Long id) {
        EmployeeDto employee = employeeMapper.toDto(employeeDao.readById(id));
        if (employee == null) {
            throw new EmployeeNotFoundException("Не нашлось работника с id= " + id);
        }
        return employee;
    }

    @Override
    public List<EmployeeDto> getAllEmployees(Integer limit, Integer page) {
        return employeeDao.getAll(limit, page)
                .stream()
                .map(employeeMapper::toDto)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        employeeDao.deleteById(id);
    }

    @Override
    public void updateEmployeeById(Long id, EmployeeDto newEmployee) {
        newEmployee.setId(id);
        employeeDao.update(id,employeeMapper.toEmployee(newEmployee));

    }

    @Override
    public EmployeeDto getEmployeeByLogin(String login) {
        return employeeMapper.toDto(employeeDao.getEmployeeByLogin(login));
    }

    @Override
    public List<EmployeeDto> getEmployeesBySurname(String surname,Integer limit,Integer page) {
        return employeeDao.getEmployeesBySurname(surname, limit, page)
                .stream()
                .map(employeeMapper::toDto)
                .toList();
    }

    @Override
    public List<EmployeeDto> getEmployeesByJobTitle(JobTitle jobTitle, Integer limit, Integer page) {
        return employeeDao.getEmployeesByJobTitle(jobTitle, limit, page)
                .stream()
                .map(employeeMapper::toDto)
                .toList();
    }

    @Override
    public List<EmployeeDto> getEmployeesByStatus(boolean status, Integer limit, Integer page) {
        return employeeDao.getEmployeesByStatus(status, limit, page)
                .stream()
                .map(employeeMapper::toDto)
                .toList();
    }

    @Override
    public List<EmployeeDto> getEmployeesByJobTitleWithStatus(JobTitle jobTitle, boolean status, Integer limit, Integer page) {
        return employeeDao.getEmployeesByJobTitleWithStatus(jobTitle,status, limit, page)
                .stream()
                .map(employeeMapper::toDto)
                .toList();
    }
}
