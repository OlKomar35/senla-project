package org.senla.komar.spring.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleDto {
    private Long id;

    @NotBlank(message = "Не введено название роли")
    @Size(min = 2, max = 25)
    private String name;

    private Set<PermissionDto> permissions;
}
