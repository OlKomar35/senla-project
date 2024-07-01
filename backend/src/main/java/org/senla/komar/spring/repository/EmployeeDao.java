package org.senla.komar.spring.repository;

import org.senla.komar.spring.entity.Employee;
import org.senla.komar.spring.enums.JobTitle;

import java.util.List;

public interface EmployeeDao extends GenericDao<Long, Employee> {
    Employee getEmployeeByLogin(String login);
    List<Employee> getEmployeesBySurname(String surname, Integer limit, Integer page);

    List<Employee> getEmployeesByJobTitle(JobTitle jobTitle, Integer limit, Integer page);

    List<Employee> getEmployeesByStatus(boolean status, Integer limit, Integer page);

    List<Employee> getEmployeesByJobTitleWithStatus(JobTitle jobTitle, boolean status, Integer limit, Integer page);

    List<Employee> getAll(Integer limit, Integer page);
}
