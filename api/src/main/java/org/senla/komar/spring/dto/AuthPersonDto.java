package org.senla.komar.spring.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthPersonDto {
    private Long id;

    @NotBlank(message = "Не введен электронный адрес")
    @Email(message = "Не является адресом электронной почты")
    @Size(min = 5, max = 50, message = "Не верный размер электронной почты")
    private String email;

    @NotBlank(message = "Не введен логин")
    @Size(min = 5, max = 100)
    private String login;

    private List<RoleDto> roles;
}
