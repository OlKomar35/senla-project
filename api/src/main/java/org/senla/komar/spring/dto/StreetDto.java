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
public class StreetDto {
    private Long id;

    @NotBlank(message = "Название улицы  не может быть пустым")
    @Size(min = 2, max = 100, message = "Не верная длина названия улицы")
    private String name;
}
