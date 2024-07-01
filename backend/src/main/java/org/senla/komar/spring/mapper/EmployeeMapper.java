package org.senla.komar.spring.mapper;

import org.mapstruct.Mapper;
import org.senla.komar.spring.dto.EmployeeDto;
import org.senla.komar.spring.dto.GuestDto;
import org.senla.komar.spring.entity.Employee;
import org.senla.komar.spring.entity.Guest;

@Mapper(componentModel = "spring")
public interface EmployeeMapper {
    Employee toEmployee(EmployeeDto employeeDto);
    EmployeeDto toDto(Employee employee);
}
