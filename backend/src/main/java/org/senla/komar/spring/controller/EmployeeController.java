package org.senla.komar.spring.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.RequiredArgsConstructor;
import org.senla.komar.spring.dto.EmployeeDto;
import org.senla.komar.spring.enums.JobTitle;
import org.senla.komar.spring.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN') OR hasRole('ROLE_MANAGER')")
@Validated
public class EmployeeController {
    private final EmployeeService employeeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createEmployee(@Valid @RequestBody EmployeeDto employeeDto) {
        employeeService.createEmployee(employeeDto);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeDto> viewAllEmployees(@RequestParam(value = "limit", defaultValue = "5")
                                              @Positive
                                              @Min(1)
                                              @Max(50)
                                              Integer limit,
                                              @RequestParam(value = "page", defaultValue = "1")
                                              @Positive
                                              @Min(1)
                                              Integer page) {
        return employeeService.getAllEmployees(limit, page);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDto getEmployeeById(@PathVariable("id") @Positive @Min(1) Long id) {
        return employeeService.getEmployeeById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteEmployeeById(@PathVariable("id") @Positive @Min(1) Long id) {
        employeeService.deleteById(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void updateEmployeeById(@PathVariable("id") @Positive @Min(1) Long id,
                                   @Valid @RequestBody EmployeeDto newEmployee) {
        employeeService.updateEmployeeById(id, newEmployee);
    }

    @GetMapping("/surname/{surname}")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeDto> getEmployeeBySurname(@PathVariable("surname") @NotBlank String surname,
                                                  @RequestParam(value = "limit", defaultValue = "5") Integer limit,
                                                  @RequestParam(value = "page", defaultValue = "1") Integer page) {
        return employeeService.getEmployeesBySurname(surname, limit, page);
    }

    @GetMapping("/login/{login}")
    @ResponseStatus(HttpStatus.OK)
    public EmployeeDto getEmployeeByLogin(@PathVariable("login") @NotBlank String login) {
        return employeeService.getEmployeeByLogin(login);
    }

    @GetMapping("/job/{jobTitle}")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeDto> getEmployeeByJobTitle(@PathVariable("jobTitle") @NotNull JobTitle jobTitle,
                                                   @RequestParam(value = "limit", defaultValue = "5")
                                                   @Positive
                                                   @Min(1)
                                                   @Max(50)
                                                   Integer limit,
                                                   @RequestParam(value = "page", defaultValue = "1")
                                                   @Positive
                                                   @Min(1)
                                                   Integer page) {
        return employeeService.getEmployeesByJobTitle(jobTitle, limit, page);
    }

    @GetMapping("/status/{status}")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeDto> getEmployeeBySurname(@PathVariable("status") @NotNull Boolean status,
                                                  @RequestParam(value = "limit", defaultValue = "5")
                                                  @Positive
                                                  @Min(1)
                                                  @Max(50)
                                                  Integer limit,
                                                  @RequestParam(value = "page", defaultValue = "1")
                                                  @Positive
                                                  @Min(1)
                                                  Integer page) {
        return employeeService.getEmployeesByStatus(status, limit, page);
    }

    @GetMapping("/job-status/{jobTitle}/{status}")
    @ResponseStatus(HttpStatus.OK)
    public List<EmployeeDto> getEmployeesByJobTitleWithStatus(@PathVariable("jobTitle") @NotNull JobTitle jobTitle,
                                                              @PathVariable("status") @NotNull Boolean status,
                                                              @RequestParam(value = "limit", defaultValue = "5")
                                                              @Positive
                                                              @Min(1)
                                                              @Max(50)
                                                              Integer limit,
                                                              @RequestParam(value = "page", defaultValue = "1")
                                                              @Positive
                                                              @Min(1)
                                                              Integer page) {
        return employeeService.getEmployeesByJobTitleWithStatus(jobTitle, status, limit, page);
    }
}
