package org.senla.komar.spring.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.senla.komar.spring.enums.JobTitle;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDto {
    private Long id;

    @NotNull
    private PersonDto personDto;

    @NotNull
    private JobTitle jobTitle;

    private boolean status;
    private List<RoleDto> roles;
}
