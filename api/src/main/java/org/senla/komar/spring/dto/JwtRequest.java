package org.senla.komar.spring.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtRequest {

    @NotBlank(message = "Не введен логин")
    @Size(min = 5, max = 100)
    private String login;

    @NotBlank(message = "Не введен пароль")
    @Size(min = 2, max = 350)
    private String password;
}
