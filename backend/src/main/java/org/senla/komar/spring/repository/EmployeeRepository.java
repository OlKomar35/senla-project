package org.senla.komar.spring.repository;

import java.util.List;
import java.util.Optional;
import org.senla.komar.spring.entity.Employee;
import org.senla.komar.spring.enums.JobTitle;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

  Optional<Employee> findByPersonLogin(String login);

  Page<Employee> findByPersonSurname(String surname, Pageable pageable);

  Page<Employee> findByJobTitle(JobTitle jobTitle, Pageable pageable);

  Page<Employee> findByStatus(boolean status, Pageable pageable);

  List<Employee> findByJobTitleAndStatus(JobTitle jobTitle, boolean status, Pageable pageable);

}
