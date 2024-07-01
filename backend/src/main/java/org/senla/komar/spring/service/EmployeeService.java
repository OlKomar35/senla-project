package org.senla.komar.spring.service;

import org.senla.komar.spring.dto.EmployeeDto;
import org.senla.komar.spring.enums.JobTitle;

import java.util.List;

public interface EmployeeService {
    void createEmployee(EmployeeDto employeeDto);

    EmployeeDto getEmployeeById(Long id);

    List<EmployeeDto> getAllEmployees(Integer limit, Integer page);

    void deleteById(Long id);

    void updateEmployeeById(Long id, EmployeeDto newEmployee);

    EmployeeDto getEmployeeByLogin(String login);

    List<EmployeeDto> getEmployeesBySurname(String surname, Integer limit, Integer page);

    List<EmployeeDto> getEmployeesByJobTitle(JobTitle jobTitle, Integer limit, Integer page);

    List<EmployeeDto> getEmployeesByStatus(boolean status, Integer limit, Integer page);

    List<EmployeeDto> getEmployeesByJobTitleWithStatus(JobTitle jobTitle, boolean status, Integer limit, Integer page);
}
